package org.example;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

public class OntologySearch {


    public static void searchInstance(Model model, int  userAge, String genre, String heroClass){
        heroClass = "http://www.semanticweb.org/slava/ontologies/2024/8/untitled-ontology-3#" + heroClass; // класс героя
        String gameURI = new String();
        if (userAge >= 18){
            userAge = 18;
        }
        else if (userAge >= 16){
            userAge = 16;
        }
        else if (userAge >= 12){
            userAge = 12;
        }
        // SPARQL-запрос
        String sparqlQueryString = String.format(
                "PREFIX ont: <http://www.semanticweb.org/slava/ontologies/2024/8/untitled-ontology-3#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "SELECT ?game " +
                        "WHERE { " +
                        "  ?game rdfs:subClassOf ont:%s . " +
                        " ?game rdfs:subClassOf ont:%s . " +
                        "}", genre, userAge);

        // Создаём запрос
        Query query = QueryFactory.create(sparqlQueryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                gameURI = results.nextSolution().getResource("game").getURI();
            }
            else {
                System.out.println("Ни одна игра не была найдена");
                return;
            }
        }
        sparqlQueryString = String.format(
                "PREFIX ont: <http://www.semanticweb.org/slava/ontologies/2024/8/untitled-ontology-3#> " +
                        "SELECT ?instance WHERE { " +
                        "  ?instance a <" + gameURI + "> . " +
                        "  ?instance ont:class <%s> . " + // используйте URI вместо строки
                        "}", heroClass);
        query = QueryFactory.create(sparqlQueryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)){
            ResultSet results = qexec.execSelect();
            if (!results.hasNext()){
                System.out.println("Ни один персонаж с подобными критериями не был найден");
                return;
            }
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Resource hero = soln.getResource("instance");
                System.out.println("Найден герой: " + hero.getLocalName());
            }

        }

    }
}
