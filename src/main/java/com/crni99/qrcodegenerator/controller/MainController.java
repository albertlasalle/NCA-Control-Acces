package com.crni99.qrcodegenerator.controller;

import java.io.IOException;
import java.sql.Date;

import java.util.List;


import com.crni99.qrcodegenerator.Repository.PartitsRepository;
import com.crni99.qrcodegenerator.Repository.TicketsRepository;
import com.crni99.qrcodegenerator.model.Partits;
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

    private final PartitsRepository partitsRepository;

    public static int idPartit;



    public MainController(QRCodeService qrCodeService, TicketsRepository repository, PartitsRepository partitsRepository) {
        this.qrCodeService = qrCodeService;
        this.repository = repository;
        this.partitsRepository = partitsRepository;
    }


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tickets", new Tickets());
        List<Partits> listaDePartits = partitsRepository.findAll();
        model.addAttribute("partits", listaDePartits);

        return "index";
    }

    @GetMapping("/partits")
    public String partits(Model model) {
        model.addAttribute("partits", new Partits());
        return "CrearPartits";
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

    @GetMapping("/comprar/partit/{id}")
    public String comprarPartit(@PathVariable("id") int id, Model model) {
        Partits partit = partitsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid partit Id:" + id));
        model.addAttribute("idPartit", id);
        idPartit = partit.getId();
        return "ComprarTicket";
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
    @ResponseBody
    public String deleteTicket(@PathVariable("token") String token, Model model) {
        Tickets ticket = repository.findById(token).orElseThrow(() -> new IllegalArgumentException("Invalid ticket Token:" + token));
        repository.delete(ticket);
        model.addAttribute("tickets", repository.findAll());
        return "redirect:/";
    }


}
