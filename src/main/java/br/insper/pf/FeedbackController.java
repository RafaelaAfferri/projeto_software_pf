package br.insper.pf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    @Autowired
    private FeebackService feedbackService;

    @PostMapping("/feedback")
    public Feedack criarFeedback(@RequestHeader(name = "Authorization") String token, @RequestBody Feedack feedback) {
        return feedbackService.createFeedback(token, feedback);
    }

    @GetMapping("/feedback")
    public List<Feedack> listFeedbacks(@RequestHeader(name = "Authorization") String token) {
        return feedbackService.listFeedbacks(token);
    }

    @GetMapping("/feedback/{id}")
    public Feedack getFeedback(@RequestHeader(name = "Authorization") String token, @PathVariable String id) {
        return feedbackService.getFeedback(token, id);
    }

    @DeleteMapping("/feedback/{id}")
    public boolean deleteFeedback(@RequestHeader(name = "Authorization") String token, @PathVariable String id) {
        return feedbackService.deleteFeedback(token, id);
    }


}
