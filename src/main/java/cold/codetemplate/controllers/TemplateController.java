package cold.codetemplate.controllers;

import cold.codetemplate.models.Template;
import cold.codetemplate.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class TemplateController {
    @Autowired
    TemplateRepository templateRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addTemplate(@RequestBody Template template){
        if (template.getName() == null){
            return new ResponseEntity<>("Name is empty", HttpStatus.BAD_REQUEST);
        }
        else if (template.getCode() == null){
            return new ResponseEntity<>("Code is empty",HttpStatus.BAD_REQUEST);
        }
        else if (template.getLanguage() == null){
            return new ResponseEntity<>("Code is empty",HttpStatus.BAD_REQUEST);
        }
        //TODO: strip whitespaces

        List<Template> templates = templateRepository.findAll();

        for (Template t : templates){
            if (Objects.equals(t.getName(), template.getName())){
                return new ResponseEntity<>("Name is already used",HttpStatus.BAD_REQUEST);
            }
        }

        templateRepository.save(template);

        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeTemplate(@RequestBody Template template){
        if (template.getName() == null){
            return new ResponseEntity<>("Name is empty",HttpStatus.BAD_REQUEST);
        }

        List<Template> templates = templateRepository.findAll();

        Template templateToRemove = null;

        for (Template t : templates){
            if (Objects.equals(t.getName(), template.getName())){
                templateToRemove = t;
                break;
            }
        }

        if (templateToRemove == null){
            return new ResponseEntity<>("Template not found",HttpStatus.BAD_REQUEST);
        }

        templateRepository.delete(templateToRemove);

        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Template>> getAllTemplates(){
        return new ResponseEntity<>(templateRepository.findAll(),HttpStatus.OK);
    }

    @PostMapping("/name")
    public ResponseEntity<Template> getTemplateByName(@RequestBody Template template){
        if (template.getName() == null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        List<Template> templates = templateRepository.findAll();
        Template templateResponse = null;

        for (Template t : templates){
            if (Objects.equals(t.getName(), template.getName())){
                templateResponse = t;
                break;
            }
        }

        if (templateResponse == null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(templateResponse,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTemplate(@RequestBody Template template){
        if (template.getName() == null){
            return new ResponseEntity<>("Name is empty", HttpStatus.BAD_REQUEST);
        }
        else if (template.getCode() == null){
            return new ResponseEntity<>("Code is empty",HttpStatus.BAD_REQUEST);
        }

        Template templateToUpdate = null;
        List<Template> templates = templateRepository.findAll();

        for (Template t : templates){
            if (Objects.equals(t.getName(), template.getName())){
                templateToUpdate = t;
                break;
            }
        }

        if (templateToUpdate == null){
            return new ResponseEntity<>("Template not found",HttpStatus.BAD_REQUEST);
        }

        templateRepository.delete(templateToUpdate);
        templateRepository.save(template);

        return new ResponseEntity<>("Done",HttpStatus.OK);
    }

    @GetMapping("/removeall")
    public ResponseEntity<String> removeAll(){
        templateRepository.deleteAll();
        return new ResponseEntity<>("Done",HttpStatus.OK);
    }
}
