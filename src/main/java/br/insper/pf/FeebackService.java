package br.insper.pf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;




@Service
public class FeebackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedack createFeedback(String token, Feedack feedback) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ReturnUsuarioDTO> response = restTemplate.exchange("http://184.72.80.215/usuario/validate", HttpMethod.GET, entity, ReturnUsuarioDTO.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            ReturnUsuarioDTO usuario = response.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        if(response.getBody()==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        ReturnUsuarioDTO usuario = response.getBody();

        if(usuario.getPapel().equals("ADMIN")){
            if(feedback.getTitulo() == null || feedback.getDescricao() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Titulo e descricao sao obrigatorios");
            }
            feedback.setId(UUID.randomUUID().toString());
            return feedbackRepository.save(feedback);
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autorizado");
        }


    }
    public List<Feedack> listFeedbacks(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ReturnUsuarioDTO> response = restTemplate.exchange("http://184.72.80.215/usuario/validate", HttpMethod.GET, entity, ReturnUsuarioDTO.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            ReturnUsuarioDTO usuario = response.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        if(response.getBody()==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        ReturnUsuarioDTO usuario = response.getBody();

        if(usuario.getPapel().equals("ADMIN") || usuario.getPapel().equals("DEVELOPERS")){
            List<Feedack> feedbacks = feedbackRepository.findAll();
            if(feedbacks.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum feedback encontrado");
            }
            return feedbacks;
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autorizado");
        }
    }

    public Feedack getFeedback(String token, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ReturnUsuarioDTO> response = restTemplate.exchange("http://184.72.80.215/usuario/validate", HttpMethod.GET, entity, ReturnUsuarioDTO.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            ReturnUsuarioDTO usuario = response.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        if(response.getBody()==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        ReturnUsuarioDTO usuario = response.getBody();

        if(usuario.getPapel().equals("ADMIN") || usuario.getPapel().equals("DEVELOPERS")) {
            Optional<Feedack> op = feedbackRepository.findById(id);
            if (op.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feedback nao encontrado");
            }
            return op.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autorizado");
        }
    }

    public boolean deleteFeedback(String token, String id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ReturnUsuarioDTO> response = restTemplate.exchange("http://184.72.80.215/usuario/validate", HttpMethod.GET, entity, ReturnUsuarioDTO.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            ReturnUsuarioDTO usuario = response.getBody();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        if(response.getBody()==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token invalido");
        }
        ReturnUsuarioDTO usuario = response.getBody();

        if(usuario.getPapel().equals("ADMIN")) {
            if (id == null || id.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id invalido");
            }
            if (feedbackRepository.existsById(id)) {
                feedbackRepository.deleteById(id);
                return true;
            }
            return false;
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autorizado");
        }
    }

}
