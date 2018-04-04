package net.proselyte.springsecurityapp;

import net.proselyte.springsecurityapp.config.jp;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Booking.History;
import net.proselyte.springsecurityapp.model.Documents.AudioVideo;
import net.proselyte.springsecurityapp.model.Documents.Book;
import net.proselyte.springsecurityapp.model.Documents.Document;
import net.proselyte.springsecurityapp.model.Users.*;
import net.proselyte.springsecurityapp.service.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {jp.class,UserServiceImpl.class,DocumentServiceImpl.class,HistoryServiceImpl.class, BCryptPasswordEncoder.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@WebAppConfiguration
public class TestCases_04_04 {

    @Autowired
    protected UserServiceImpl userService;

    @Autowired
    protected DocumentServiceImpl documentService;

    @Autowired
    protected HistoryServiceImpl historyService;

    @Resource
    private UserDao userRepository;

    Librarian l;
    Professor p1, p2, p3;
    Student s;
    VisitingProfessor v;

    Book d1, d2;
    AudioVideo d3;

    DateFormat format;

    @Before
    public void createItems(){
        format = new SimpleDateFormat("dd mm");

        l = new Librarian();
        l.setName("libr");
        l.setSurname("libr");
        l.setPhone("14837");
        l.setId((long) 0);
        l.setType("Librarian");
        l.setEmail("esdfghjjgtbnbnbnbgnzz");
        l.setUsername("dfghjkl,fghjkxx");
        l.setPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        l.setConfirmPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        l.userService = userService;
        l.docService = documentService;
        userService.save(l);

        p1 = new Professor();
        p1.setName("Sergey");
        p1.setSurname("Afonso");
        p1.setAddress("Via Margutta, 3");
        p1.setPhone("30001");
        p1.setId((long) 1010);
        p1.setType("Professor");
        p1.setEmail("esdfghjjgtbnbnbnbgn");
        p1.setUsername("dfghjkl,fghjk");
        p1.setPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        p1.setConfirmPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");

        p2 = new Professor();
        p2.setName("Nadia");
        p2.setSurname("Teixeira");
        p2.setUsername("dfghjkl,fghjks");
        p2.setAddress("Via Sacra, 13");
        p2.setPhone("30002");
        p2.setId((long) 1011);
        p2.setPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        p2.setConfirmPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        p2.setType("Professor");
        p2.setEmail("esdfghjjgtbnbnbgn");

        p3 = new Professor();
        p3.setName("Elvira");
        p3.setSurname("Espindola");
        p3.setAddress("Via del Corso, 22");
        p3.setPhone("30003");
        p3.setId((long) 1100);
        p3.setType("Professor");
        p3.setPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        p3.setConfirmPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        p3.setUsername("dfghjkl,fghjka");
        p3.setEmail("esdfghjjgtbnbnbnbn");

        s = new Student();
        s.setName("Andrey");
        s.setSurname("Velo");
        s.setAddress("Avenida Mazatlan 250");
        s.setPhone("30004");
        s.setId((long) 1101);
        s.setType("Student");
        s.setPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        s.setConfirmPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        s.setUsername("dfghjkl,fghjkaq");
        s.setEmail("esdfghjjgtbnbnbnbnqq");

        v = new VisitingProfessor();
        v.setName("Andrey");
        v.setSurname("Velo");
        v.setAddress("Avenida Mazatlan 250");
        v.setPhone("30004");
        v.setId((long) 1110);
        v.setType("Student");
        v.setPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        v.setConfirmPassword("$2a$11$KaBxQDYikh.EWsYw5Bo0B.6G7FYWZN2rVdelaZWT6.zDHXwlJCju6");
        v.setUsername("dfghjkl,fghjkaqpp");
        v.setEmail("esdfghjjgtbnbnbnbnqqpp");


        l.addPatron(p1);
        l.addPatron(p2);
        l.addPatron(p3);
        l.addPatron(s);
        l.addPatron(v);

        p1.setDocumentService(documentService);
        p1.setUserService(userService);
        p1.setHistoryService(historyService);

        p2.setDocumentService(documentService);
        p2.setUserService(userService);
        p2.setHistoryService(historyService);

        p3.setDocumentService(documentService);
        p3.setUserService(userService);
        p3.setHistoryService(historyService);

        s.setDocumentService(documentService);
        s.setUserService(userService);
        s.setHistoryService(historyService);

        v.setDocumentService(documentService);
        v.setUserService(userService);
        v.setHistoryService(historyService);

        DateFormat format = new SimpleDateFormat("mm yyyy");
        d1 = new Book();
        d1.setTitle("Intoduction to Algorithms");
        d1.setAuthors("Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein");
        d1.setPublisher("MIT Press");
        try {
            d1.setYear(format.parse("01 2009"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        d1.setEdition(3);
        d1.setBestSeller(false);
        d1.setPrice(5000);
        d1.setCopies(3);

        d2 = new Book();
        d2.setTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        d2.setAuthors("Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm");
        d2.setPublisher("Addison-Wesley Professional");
        try {
            d2.setYear(format.parse("01 2003"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        d2.setEdition(1);
        d2.setBestSeller(true);
        d2.setPrice(1700);
        d2.setCopies(3);

        d3 = new AudioVideo();
        d3.setTitle("Null References: The Billion Dollar Mistake");
        d3.setAuthors("Tony Hoare");
        d3.setPrice(700);
        d3.setCopies(2);

        l.addDoc(d1, d1.getCopies());
        l.addDoc(d2, d2.getCopies());
        l.addDoc(d3, d3.getCopies());
    }

    @Test
    public void testUnit(){
        User user = userService.findByUsername("Test");
        System.out.println(user);
    }

    @Test
    public void testCase1(){
        p1.checkout(d1);
        try {
            historyService.getHistoryByIdAndDocId(p1.getId(), d1.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        p1.getDocuments().get(p1.getDocuments().size() - 1).setCheckoutDate("05 03");

        p1.checkout(d2);
        p1.getDocuments().get(p1.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(p1.getId(), d2.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        p1.toReturn(d2);
        ///return 2nd April////

        System.out.println(l.checkInfo(p1));
    }

    @Test
    public void testCase2(){
        p1.checkout(d1);
        p1.getDocuments().get(p1.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(p1.getId(), d1.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        p1.checkout(d2);
        p1.getDocuments().get(p1.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(p1.getId(), d2.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        s.checkout(d1);
        s.getDocuments().get(s.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(s.getId(), d1.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        s.checkout(d2);
        s.getDocuments().get(s.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(s.getId(), d2.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        v.checkout(d1);
        v.getDocuments().get(v.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(s.getId(), d1.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        v.checkout(d2);
        v.getDocuments().get(v.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(s.getId(), d2.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ///checkouts at 5 March////

        try {
            System.out.println(l.checkOverdue(p1, format.parse("02 04")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(l.checkOverdue(s, format.parse("02 04")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(l.checkOverdue(v, format.parse("02 04")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ///check overdue 2nd April////
    }

    @Test
    public void testCase3(){
        p1.checkout(d1);
        p1.getDocuments().get(p1.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(p1.getId(), d1.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        s.checkout(d2);
        s.getDocuments().get(s.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(s.getId(), d2.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        v.checkout(d2);
        v.getDocuments().get(v.getDocuments().size() - 1).setCheckoutDate("05 03");
        try {
            historyService.getHistoryByIdAndDocId(s.getId(), d2.getId()).setCheckOutDate(format.parse("05 03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        p1.renew(d1);
        s.renew(d2);
        v.renew(d2);

        try {
            System.out.println(l.checkOverdue(p1, format.parse("02 04")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(l.checkOverdue(s, format.parse("02 04")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(l.checkOverdue(v, format.parse("02 04")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    
}