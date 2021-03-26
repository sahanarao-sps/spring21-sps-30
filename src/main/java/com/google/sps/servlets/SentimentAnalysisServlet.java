package com.google.sps.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

@WebServlet("/sentiment")
public class SentimentAnalysisServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = request.getParameter("user-message");

        System.out.println(message);
        Document doc =
            Document.newBuilder().setContent(message).setType(Document.Type.PLAIN_TEXT).build();
        LanguageServiceClient languageService = LanguageServiceClient.create();
        Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
        float score = sentiment.getScore();
        languageService.close();

        // Output the sentiment score as HTML.
        // A real project would probably store the score alongside the content.
        response.setContentType("text/html;");
        response.getWriter().println("<h1>Sentiment Analysis</h1>");
        response.getWriter().println("<p>You entered: " + message + "</p>");
        response.getWriter().println("<p>Sentiment analysis score: " + score + "</p>");
        response.getWriter().println("<p><a href=\"/\">Back</a></p>");
    }

}
