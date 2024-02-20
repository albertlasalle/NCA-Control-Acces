package com.crni99.qrcodegenerator.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import java.util.Base64;
import java.util.List;


import com.crni99.qrcodegenerator.Repository.AdminRepository;
import com.crni99.qrcodegenerator.Repository.PartitsRepository;
import com.crni99.qrcodegenerator.Repository.TicketsRepository;
import com.crni99.qrcodegenerator.model.Admin;
import com.crni99.qrcodegenerator.model.Partits;
import com.crni99.qrcodegenerator.model.Tickets;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crni99.qrcodegenerator.service.QRCodeService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class MainController {

    private final QRCodeService qrCodeService;

    private final TicketsRepository repository;

    private final PartitsRepository partitsRepository;

    private final AdminRepository AdminRepository;

    public static int idPartit;


    public MainController(QRCodeService qrCodeService, TicketsRepository repository, PartitsRepository partitsRepository, AdminRepository AdminRepository) {
        this.qrCodeService = qrCodeService;
        this.repository = repository;
        this.partitsRepository = partitsRepository;
        this.AdminRepository = AdminRepository;
    }

    @Autowired
    public HttpSession session;

    public String usuari;

    public String url;

    public int idPartitEntrada = 0;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tickets", new Tickets());
        List<Partits> listaDePartits = partitsRepository.findAll();
        model.addAttribute("partits", listaDePartits);

        return "index";
    }

    @GetMapping("/decode")
    public String decodeQRCode(HttpServletRequest request) {

        String admin = (String) session.getAttribute("admin");

        if (admin != null && admin.equals(usuari)) {
            return "decode";
        } else {

            url = request.getRequestURI();
            session.setAttribute("redirectUrl", url);
            return "redirect:/LoginAdmin";
        }

    }

    @PostMapping("/decode")
    public String PostdecodeQRCode() {

        String admin = (String) session.getAttribute("admin");

        if (admin != null && admin.equals(usuari)) {
            return "decode";
        } else {
            return "redirect:/LoginAdmin";
        }

    }

    @GetMapping("/editarPartits")
    public String VistaEditarPartits(Model model, HttpServletRequest request) {

        String admin = (String) session.getAttribute("admin");

        if (admin != null && admin.equals(usuari)) {

            model.addAttribute("partits", new Partits());
            List<Partits> listaDePartits = partitsRepository.findAll();
            model.addAttribute("partits", listaDePartits);

            return "VistaEditarPartits";

        } else {
            url = request.getRequestURI();
            session.setAttribute("redirectUrl", url);
            return "redirect:/LoginAdmin";
        }
    }

    @PostMapping("/editarPartits")
    public String PostVistaEditarPartits(Model model, HttpServletRequest request) {

        String admin = (String) session.getAttribute("admin");

        if (admin != null && admin.equals(usuari)) {

            model.addAttribute("partits", new Partits());
            List<Partits> listaDePartits = partitsRepository.findAll();
            model.addAttribute("partits", listaDePartits);

            return "VistaEditarPartits";
        } else {
            url = request.getRequestURI();
            session.setAttribute("redirectUrl", url);
            return "redirect:/LoginAdmin";
        }
    }

    @GetMapping("/esborrar/partit/{id}")
    public String esborrarPartit(@PathVariable("id") int id) {
        try {
            partitsRepository.deleteById(id);
        } catch (Exception e) {
            String error = "No s'ha pogut esborrar el partit amb codi: " + id;
            return "redirect:/partits?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
        }
        return "redirect:/editarPartits";
    }

    @GetMapping("/editar/partit/{id}")
    public String editarPartit(@PathVariable("id") int id, Model model) {

        Partits partit = partitsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid partit Id:" + id));
        model.addAttribute("partit", partit);
        return "EditarPartits";

    }

    @PostMapping("/actualitzar/partit/{id}")
    public String actualitzarPartit(@RequestParam("id") int id, @RequestParam("nomPartit") String nomPartit, @RequestParam("preu") int preu, @RequestParam("poblacio") String poblacio, @RequestParam("dia") Date dia, @RequestParam("horaInici") String horaInici, @RequestParam("horaFi") String horaFi, Partits partits) {
        if (nomPartit == null || nomPartit.isBlank() || nomPartit.isEmpty()) {
            return "redirect:/editarPartits";
        }

        partits.setId(id);
        partits.setPartit(nomPartit);
        partits.setPreu(preu);
        partits.setPoblacio(poblacio);
        partits.setDia(dia);
        partits.setHoraInici(horaInici);
        partits.setHoraAcaba(horaFi);
        partitsRepository.save(partits);

        return "redirect:/editarPartits";
    }


    @GetMapping("/partits")
    public String partits(Model model, HttpServletRequest request) {

        String admin = (String) session.getAttribute("admin");

        if (admin != null && admin.equals(usuari)) {
            model.addAttribute("partits", new Partits());
            return "CrearPartits";

        } else {

            url = request.getRequestURI();
            session.setAttribute("redirectUrl", url);
            return "redirect:/LoginAdmin";
        }

    }

    @PostMapping("/partits")
    public String Postpartits(Model model) {

        String admin = (String) session.getAttribute("admin");
        if (admin != null && admin.equals(usuari)) {
            model.addAttribute("partits", new Partits());
            return "CrearPartits";
        } else {
            return "redirect:/LoginAdmin";
        }

    }

    @PostMapping("/generate")
    public String generateQRCode(@RequestParam("text") String text, Model model, Tickets tickets, @RequestParam("data_compra") Date data_compra, @RequestParam("id_partit") int idPartit, @RequestParam("dni_usuari") String dniUsuari, @RequestParam("correu") String correu, @RequestParam("telefon_movil") int telefonMovil, @RequestParam("nom") String nom, @RequestParam("edat") int edat) {
        if (text == null || text.isBlank() || text.isEmpty()) {
            return "redirect:/";
        }

        String qrCode = qrCodeService.getQRCode(text);
        model.addAttribute("text", text);
        model.addAttribute("qrcode", qrCode);


        tickets.setToken(text);
        tickets.setIdPartit(idPartit);
        tickets.setDniUsuari(dniUsuari);
        tickets.setNomUsuari(nom);
        tickets.setEdatUsuari(edat);
        tickets.setCorreu(correu);
        tickets.setTelefonMovil(telefonMovil);
        tickets.setDataCompra(data_compra);
        repository.save(tickets);

        idPartitEntrada = idPartit;

        return "ComprarTicket";
    }


    @GetMapping("/comprar/partit/{id}")
    public String comprarPartit(@PathVariable("id") int id, Model model) {
        Partits partit = partitsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid partit Id:" + id));
        model.addAttribute("idPartit", id);
        idPartit = partit.getId();
        return "ComprarTicket";
    }

    @GetMapping("/LoginAdmin")
    public String admin(Model model) {
        model.addAttribute("admin", new Admin());
        return "LoginAdmin";
    }

    @PostMapping("/admin")
    public String loginAdmin(@RequestParam("user") String usuari, @RequestParam("password") String contrasenya) {
        if (usuari == null || usuari.isBlank() || usuari.isEmpty()) {
            return "redirect:/admin";
        }
        if (contrasenya == null || contrasenya.isBlank() || contrasenya.isEmpty()) {
            return "redirect:/admin";
        }
        List<Admin> admins = AdminRepository.findAll();
        for (Admin admin : admins) {
            if (admin.getUsuari().equals(usuari) && admin.getContrasenya().equals(contrasenya)) {
                this.usuari = admin.getUsuari();
                session.setAttribute("admin", this.usuari);


                String redirectUrl = (String) session.getAttribute("redirectUrl");
                if (redirectUrl != null) {
                    // Eliminar la URL almacenada de la sesión
                    session.removeAttribute("redirectUrl");
                    return "redirect:" + redirectUrl;
                }


                return "admin";


            }
        }
        return "redirect:/admin";
    }


    @PostMapping("/createPartits")
    public String createPartit(@RequestParam("nomPartit") String nomPartit, @RequestParam("preu") int preu, @RequestParam("poblacio") String poblacio, @RequestParam("dia") Date dia, @RequestParam("horaInici") String horaInici, @RequestParam("horaFi") String horaFi, Partits partits) {
        if (nomPartit == null || nomPartit.isBlank() || nomPartit.isEmpty()) {
            return "redirect:/partits";
        }

        System.out.println(horaInici);
        System.out.println(horaFi);

        partits.setPartit(nomPartit);
        partits.setPreu(preu);
        partits.setPoblacio(poblacio);
        partits.setDia(dia);
        partits.setHoraInici(horaInici);
        partits.setHoraAcaba(horaFi);
        partitsRepository.save(partits);

        return "CrearPartits";
    }


    @GetMapping("/logout")
    public String invalidateSession() {
        session.setAttribute("admin", null);
        return "redirect:/";
    }


    @PostMapping("/uploadQrCode")
    public String uploadQrCode(@RequestParam("qrCodeFile") MultipartFile qrCodeFile,
                               RedirectAttributes redirectAttributes) {
        if (qrCodeFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please choose file to upload.");
            return "redirect:/decode";
        }
        try {
            String qrContent = qrCodeService.decodeQR(qrCodeFile.getBytes());
            redirectAttributes.addFlashAttribute("qrContent", qrContent);

            return "redirect:/decode";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/decode";
    }

    @PostMapping("/VerificarEntrada")
    @ResponseBody
    public Boolean verificarToken(@RequestParam("token") String token) {

        List<Tickets> RevisarTickets = repository.findAll();

        for (Tickets tickets : RevisarTickets) {
            if (tickets.getToken().equals(token)) {

                return true;

            }
        }

        return false;
    }

    @DeleteMapping("/delete/{token}")
    @ResponseBody
    public String deleteTicket(@PathVariable("token") String token, Model model) {
        Tickets ticket = repository.findById(token).orElseThrow(() -> new IllegalArgumentException("Invalid ticket Token:" + token));
        repository.delete(ticket);
        model.addAttribute("tickets", repository.findAll());
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
