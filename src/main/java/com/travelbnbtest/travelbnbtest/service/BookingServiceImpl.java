package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.controller.BookingController;
import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Booking;
import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.BookingDto;
import com.travelbnbtest.travelbnbtest.repository.BookingRepository;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookingServiceImpl implements BookingService{
        private BookingRepository bookingRepository;
        private PropertyRepository propertyRepository;
        private BucketService bucketService;
        private PDFService pdfService;

        private TwilioService twilioService;

    public BookingServiceImpl(BookingRepository bookingRepository, PropertyRepository propertyRepository, BucketService bucketService, PDFService pdfService, TwilioService twilioService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.pdfService = pdfService;
        this.twilioService = twilioService;
    }

    @Override
    public BookingDto createBooking(long propertyId, AppUser user, BookingDto bookingDto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFoundException("Property not found with id: "+propertyId)
        );
        bookingDto.setProperty(property);
        bookingDto.setAppUser(user);

        int nightlyPrice = property.getPrice();
        int totalPrice = nightlyPrice * bookingDto.getTotalNights();
        int gstAmount = (totalPrice * 18) / 100;
        int finalPrice = totalPrice + gstAmount;
        bookingDto.setPrice(finalPrice);

        Booking booking = dtoToEntity(bookingDto);
        Booking savedBooking = bookingRepository.save(booking);
        boolean b = pdfService.generatePDF("D://SK//" + "booking-confirmation-id" + savedBooking.getId() + ".pdf", bookingDto);
        if (b){
            try {
                MultipartFile file = BookingController.convert("D://SK//" + "booking-confirmation-id" + savedBooking.getId() + ".pdf");
                //
                String uploadedFileUrl = bucketService.uploadFile(file, "sachin2002");
                System.out.println(uploadedFileUrl);
                //for whatsapp
                String whatsappId = twilioService.sendWhatsAppMessage(bookingDto.getMobile(), "Your booking is confirmed..Click for deatils: " + uploadedFileUrl);
                System.out.println(whatsappId);
                //for sms
                String smsId = twilioService.sendSms(bookingDto.getMobile(), "Your booking is confirmed..Click for deatils: " + uploadedFileUrl);
                System.out.println(smsId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        BookingDto dto = entityToDto(savedBooking);
        return dto;

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
        return dto;
    }
}
