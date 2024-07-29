package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.controller.BookingController;
import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Booking;
import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.entity.Room;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.BookingDto;
import com.travelbnbtest.travelbnbtest.repository.BookingRepository;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import com.travelbnbtest.travelbnbtest.repository.RoomRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
        private BookingRepository bookingRepository;
        private PropertyRepository propertyRepository;
        private BucketService bucketService;
        private PDFService pdfService;
        private RoomRepository roomRepository;

        private TwilioService twilioService;

    public BookingServiceImpl(BookingRepository bookingRepository, PropertyRepository propertyRepository, BucketService bucketService, PDFService pdfService, RoomRepository roomRepository, TwilioService twilioService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.pdfService = pdfService;
        this.roomRepository = roomRepository;
        this.twilioService = twilioService;
    }

    @Transactional
    @Override
    public BookingDto createBooking(long propertyId, AppUser user, BookingDto bookingDto, long roomId) {

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
            // Check if the room is already booked
            if (room.isStatus()) {
                throw new ResourceNotFoundException("Room is already booked.");
            }
            // Set the room status to booked
            room.setStatus(true);
            roomRepository.save(room);

            // Get the property details
            Property property = propertyRepository.findById(propertyId).orElseThrow(
                    () -> new ResourceNotFoundException("Property not found with id: " + propertyId)
            );
            bookingDto.setRoom(room);
            bookingDto.setProperty(property);
            bookingDto.setAppUser(user);

            // Calculate the final price
            int nightlyPrice = property.getNightlyPrice();
            int totalPrice = nightlyPrice * bookingDto.getTotalNights();
            int gstAmount = (totalPrice * 18) / 100;
            int finalPrice = totalPrice + gstAmount;
            bookingDto.setPrice(finalPrice);

            // Save the booking
            Booking booking = dtoToEntity(bookingDto);
            Booking savedBooking = bookingRepository.save(booking);

            // Generate and upload the booking confirmation PDF
            boolean b = pdfService.generatePDF("D://SK//" + "booking-confirmation-id" + savedBooking.getId() + ".pdf", bookingDto);
            if (b) {
                try {
                    MultipartFile file = BookingController.convert("D://SK//" + "booking-confirmation-id" + savedBooking.getId() + ".pdf");
                    //upload the booking confirmation PDF to S3
                    String uploadedFileUrl = bucketService.uploadFile(file, "sachin2002");
                    System.out.println(uploadedFileUrl);
                    //for whatsapp notification
                    String whatsappId = twilioService.sendWhatsAppMessage(bookingDto.getMobile(), "Your booking is confirmed..Click for details: " + uploadedFileUrl);
                    System.out.println(whatsappId);
                    //for  notification
                    String smsId = twilioService.sendSms(bookingDto.getMobile(), "Your booking is confirmed..Click for details: " + uploadedFileUrl);
                    System.out.println(smsId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            BookingDto dto = entityToDto(savedBooking);
            return dto;

        }catch (ObjectOptimisticLockingFailureException e) {
            throw new ResourceNotFoundException( "Failed to book room due to concurrent updates. Please try again.");
        }
    }

    @Override
    public List<Room> getAvailableRooms(long propertyId) {
        return roomRepository.findByPropertyIdAndStatus(propertyId,false);
    }

    //dto to entity
    Booking dtoToEntity(BookingDto bookingDto){
        Booking booking=new Booking();
        booking.setName(bookingDto.getName());
        booking.setEmail(bookingDto.getEmail());
        booking.setMobile(bookingDto.getMobile());
        booking.setPrice(bookingDto.getPrice());
        booking.setTotalNights(bookingDto.getTotalNights());
        booking.setProperty(bookingDto.getProperty());
        booking.setAppUser(bookingDto.getAppUser());
        booking.setRoom(bookingDto.getRoom());
        System.out.println(bookingDto.getRoom());
        
        return booking;
    }
    //entity to Dto
    BookingDto entityToDto(Booking booking){
        BookingDto dto=new BookingDto();
        dto.setId(booking.getId());
        dto.setName(booking.getName());
        dto.setEmail(booking.getEmail());
        dto.setMobile(booking.getMobile());
        dto.setTotalNights(booking.getTotalNights());
        dto.setPrice(booking.getPrice());
        dto.setProperty(booking.getProperty());
        dto.setAppUser(booking.getAppUser());
        dto.setRoom(booking.getRoom());
        return dto;
    }
}
