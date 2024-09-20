package org.example;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String ontologyURI = "D:\\lab1.rdf";
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        try {
            InputStream inputStream = new FileInputStream(ontologyURI);
            if (inputStream == null){
                throw new IllegalArgumentException("Онтология не была найдена по URI" + ontologyURI);
            }
            model.read(inputStream, null);
        } catch (Exception e){
            System.err.println("Ошибка загрузки онтологии " + ontologyURI);
            e.printStackTrace();
        }
        System.out.println("Введите строку с формате: I'm {years}, genre: {MOBA/Shooter}, class: {carry, support, tank}");
        while (true) {
            Scanner in = new Scanner(System.in);
            String context = in.nextLine().trim();
            if (context.equals("0")){
                System.exit(0);
            }
            if (InputValidator.validateInput(context)) {
                ArrayList<String> words = InputParser.parseInput(context);
                OntologySearch.searchInstance(model, Integer.parseInt(words.get(0)), words.get(1), words.get(2));
            } else {
                System.out.println("Некорректный ввод, попробуйте еще раз!");
            }
            System.out.println("если хотите завершить работу, введите 0");
        }

    }
}