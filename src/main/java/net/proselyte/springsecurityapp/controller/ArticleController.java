package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.Booking.History;
import net.proselyte.springsecurityapp.model.Documents.Article;
import net.proselyte.springsecurityapp.model.Documents.AudioVideo;
import net.proselyte.springsecurityapp.model.Documents.Document;
import net.proselyte.springsecurityapp.model.Users.User;
import net.proselyte.springsecurityapp.service.ArticleService;
import net.proselyte.springsecurityapp.service.DocumentService;
import net.proselyte.springsecurityapp.service.HistoryService;
import net.proselyte.springsecurityapp.service.UserService;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ArticleController {
    private final org.jboss.logging.Logger logger = LoggerFactory.logger(BookController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private DocumentService docService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/addArticle", method = RequestMethod.GET)
    public String addArticle(Model model) {
        model.addAttribute("articleForm", new Article());

        return "addArticle";

    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public String addArticle(@ModelAttribute("articleForm") Article articleForm, BindingResult bindingResult, Model model) {
        //TODO: Article Validation
        // articleValidator.validate(articleForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "addArticle";
        }

        docService.save(articleForm);

        return "redirect:/admin";

    }


    @RequestMapping(value = "/editArticle/{id}", method = RequestMethod.GET)
    public String editInfo(@PathVariable("id") Long id , Model model) {
        Document article = docService.getDocumentById(id);

        if(article!=null)
            logger.info("Article got by ID: "+article.toString());

        model.addAttribute("articleForm", article);

        return "editArticle";
    }

    @RequestMapping(value = "/editArticle/{id}",method = RequestMethod.POST)
    public String editInfo(@ModelAttribute("articleForm") Article articleForm, BindingResult bindingResult, Model model){
        docService.update(articleForm);

        logger.info("Article updated: "+ articleForm.toString());
        return "redirect:/listOfArticles";
    }

    @RequestMapping("/deleteArticle/{id}")
    public String deleteArticle(@PathVariable("id") Long id){
        docService.delete(id);

        return "redirect:/listOfArticles";
    }

    @RequestMapping(value = "/listOfArticlesForPatron", method = RequestMethod.GET)
    public String listOfArticlesForPatron(Model model) {
        //TODO: user Cookie for that
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUser);
        Long userId = user.getId();

        List<Article> articleList = docService.getListOfArticle();

        for(Article article: articleList){
            Long articleId  = article.getId();
            History userHistory = historyService.getHistoryByIdAndDocId(userId, articleId);
            int status = 1;
            if (userHistory!=null) status = userHistory.getStatus();

            if(status != 0 ){
                if(article.getCopies() == 0) status = 2;  //Go to Queue
                else status = 3;                            //Simple CheckOut
            }                                                 //else Renew + Return
            article.setStatus(status);
        }

        model.addAttribute(articleList);
        return "listOfArticlesForPatron";
    }

    @RequestMapping(value = "/listOfArticles", method = RequestMethod.GET)
    public String listOfArticles(Model model){
        List<Article> articleList = docService.getListOfArticle();
        model.addAttribute(articleList);
        return "listOfArticles";
    }

}
