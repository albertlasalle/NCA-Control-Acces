package com.crni99.qrcodegenerator.controller;

import java.io.IOException;
import java.sql.Date;

import java.util.List;


import com.crni99.qrcodegenerator.Repository.TicketsRepository;
import com.crni99.qrcodegenerator.model.Tickets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crni99.qrcodegenerator.service.QRCodeService;


@Controller
public class MainController {

    private final QRCodeService qrCodeService;

    private final TicketsRepository repository;

    public MainController(QRCodeService qrCodeService, TicketsRepository repository) {
        this.qrCodeService = qrCodeService;
        this.repository = repository;
    }


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tickets", new Tickets());
        return "index";
    }

    @PostMapping("/generate")
    public String generateQRCode(@RequestParam("text") String text, Model model, Tickets tickets, @RequestParam("data_compra") Date data_compra, @RequestParam("id_partit") int idPartit, @RequestParam("id_usuari") int idusuari) {
        if (text == null || text.isBlank() || text.isEmpty()) {
            return "redirect:/";
        }

        String qrCode = qrCodeService.getQRCode(text);
        model.addAttribute("text", text);
        model.addAttribute("qrcode", qrCode);
        tickets.setToken(text);
        tickets.setIdUsuari(idusuari);
        tickets.setIdPartit(idPartit);
        tickets.setDataCompra(data_compra);
        repository.save(tickets);

        return "index";
    }

    @GetMapping("/decode")
    public String decodeQRCode() {
        return "decode";
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
    public String deleteTicket(@PathVariable("token") String token, Model model) {
        Tickets ticket = repository.findById(token).orElseThrow(() -> new IllegalArgumentException("Invalid ticket Token:" + token));
        repository.delete(ticket);
        model.addAttribute("tickets", repository.findAll());
        return "redirect:/";
    }


}
