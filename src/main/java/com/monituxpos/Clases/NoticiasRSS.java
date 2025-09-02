/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

/**
 *
 * @author Miguel Cerrato
 */

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class NoticiasRSS {

    private List<String> titularesYaMostrados = new ArrayList<>();
    private List<String[]> titularesRSS = new ArrayList<>();
    private int indiceActual = 0;
    private int titularesMostrados = 0;

    public void cargarTitularesRSS(String url) {
        if (url == null || url.isBlank()) {
            url = "https://www.tunota.com/rss/honduras-hoy.xml";
        }

        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(url)));

            List<String[]> nuevosTitulares = feed.getEntries().stream()
                .sorted(Comparator.comparing(SyndEntry::getPublishedDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(20)
                .filter(entry -> !titularesYaMostrados.contains(entry.getTitle().trim()))
                .map(entry -> new String[] {
                    "" + entry.getTitle().trim(),
                    entry.getLink() != null ? entry.getLink() : ""
                })
                .collect(Collectors.toList());

            for (String[] titular : nuevosTitulares) {
                titularesYaMostrados.add(titular[0]);
            }

           titularesRSS = !nuevosTitulares.isEmpty()
    ? nuevosTitulares
    : Collections.singletonList(new String[] { "No hay titulares nuevos.", "" });

        } catch (Exception ex) {
           titularesRSS = Collections.singletonList(new String[] { "No se pudieron cargar las noticias: " + ex.getMessage(), "" });

        }

        indiceActual = 0;
        titularesMostrados = 0;
        mostrarSiguienteTitular();
    }

    public void mostrarSiguienteTitular() {
        if (titularesRSS.isEmpty()) return;
        String[] titular = titularesRSS.get(indiceActual);
        System.out.println(titular[0] + "\n" + titular[1]);
        // Aquí puedes actualizar tu interfaz gráfica o lógica de presentación
    }
    
    
    public List<String[]> getTitulares() {
    return titularesRSS;
}

    
    
}
