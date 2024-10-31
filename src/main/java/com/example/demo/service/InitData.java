package com.example.demo.service;

import com.example.demo.entity.ArticleEntity;
import com.example.demo.entity.ClientEntity;
import com.example.demo.entity.FactureEntity;
import com.example.demo.entity.LigneFactureEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    public InitData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }

    private void insertTestData() {
        ArticleEntity a1 = createArticle("Les conserves de viande de licorne", 22.98, "https://static.hitek.fr/img/actualite/2016/08/26/41gn6tpvqtl.jpg", 1);
        ArticleEntity a2 = createArticle("Wenger Couteau suisse géant", 46.39, "https://static.hitek.fr/img/actualite/2016/08/26/61abqa-gt8s-sx522.jpg", 40);
        ArticleEntity a3 = createArticle("PAPIER TOILETTE DONALD TRUMP", 4.99, "https://static.hitek.fr/img/actualite/2016/08/26/61cb4xnrbol-sx522.jpg", 100);
        ArticleEntity a4 = createArticle("Grattoir pour Chat en Forme de Platine de DJ", 23.14, "https://static.hitek.fr/img/actualite/2016/08/26/61grir, 20ay9-l-sx522.jpg", 100);
        ArticleEntity a5 = createArticle("Jay nothing", 2, "https://static.hitek.fr/img/actualite/2016/08/26/61vu-jqjygl-sy679.jpg", 0);
        ArticleEntity a6 = createArticle("UN AFFINEUR DE VISAGE", 52, "https://static.hitek.fr/img/actualite/2016/08/26/w_41r-1yapf5l.jpg", 20);

        ClientEntity c1 = createClient("John", "Doe", LocalDate.of(1996, 4, 30));
        ClientEntity c2 = createClient("Mickael", "Gerrin", LocalDate.of(1990, 1, 2));
        ClientEntity c3 = createClient("Jean-Michel", "Napasdargent", LocalDate.of(1980, 1, 2));

        FactureEntity f1 = createFacture(true, LocalDate.of(2023, 2, 1), c1);
        LigneFactureEntity lf11 = createLigneFacture(1, f1, a1);
        LigneFactureEntity lf12 = createLigneFacture(6, f1, a3);

        FactureEntity f2 = createFacture(true, LocalDate.of(2023, 2, 15), c1);
        LigneFactureEntity lf21 = createLigneFacture(1, f2, a1);
        LigneFactureEntity lf22 = createLigneFacture(6, f2, a3);
        LigneFactureEntity lf23 = createLigneFacture(4, f2, a6);

        FactureEntity f3 = createFacture(true, LocalDate.of(2022, 2, 15), c2);
        LigneFactureEntity lf31 = createLigneFacture(1, f3, a2);
    }

    private LigneFactureEntity createLigneFacture(int quantite, FactureEntity facture, ArticleEntity article) {
        LigneFactureEntity ligneFacture = new LigneFactureEntity();
        ligneFacture.setQuantite(quantite);
        ligneFacture.setArticle(article);
        entityManager.persist(ligneFacture);
        facture.getLigneFactures().add(ligneFacture);
        return ligneFacture;
    }

    private FactureEntity createFacture(boolean isPaye, LocalDate dateFinalisation, ClientEntity client) {
        FactureEntity facture = new FactureEntity();
        facture.setClient(client);
        entityManager.persist(facture);
        return facture;
    }

    private ClientEntity createClient(String prenom, String nom, LocalDate dateNaissance) {
        ClientEntity client = new ClientEntity();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setDateNaissance(dateNaissance);
        entityManager.persist(client);
        return client;
    }

    private ArticleEntity createArticle(String libelle, double prix, String imageUrl, Integer stock) {
        ArticleEntity a1 = new ArticleEntity();
        a1.setLibelle(libelle);
        a1.setPrix(prix);
        a1.setImageUrl(imageUrl);
        entityManager.persist(a1);
        return a1;
    }
}
