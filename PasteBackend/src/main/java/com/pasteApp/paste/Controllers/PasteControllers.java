package com.pasteApp.paste.Controllers;

import com.pasteApp.paste.Entity.PasteEntry;
import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Services.PasteServices;
import com.pasteApp.paste.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@CrossOrigin(origins = "*")
@RequestMapping("/paste")
@RestController
public class PasteControllers {
    @Autowired
    private PasteServices pasteServices;
    @Autowired
    private UserServices userServices;

    @PostMapping
    public ResponseEntity<?> CreatePaste(@RequestBody PasteEntry pasteEntry){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        pasteServices.savePaste(pasteEntry,userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<?> GetAllPasteOfUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByuserName(userName);
        List<PasteEntry> all=user.getPasteEntries();
        if(all!=null && !all.isEmpty()){
            return  new ResponseEntity<>(all,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPasteById(@PathVariable String id){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByuserName(userName);
        if(user.getPasteEntries().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<PasteEntry> paste = user.getPasteEntries().stream()
                .filter(x -> x.get_id().equals(id))
                .findFirst();
        if(paste.isPresent()){
            return new ResponseEntity<>(paste.get(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> EditPaste(@PathVariable String id,@RequestBody PasteEntry pasteEntry){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByuserName(userName);

        if(user.getPasteEntries().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<PasteEntry> paste=user.getPasteEntries().stream().filter(x->x.get_id().equals(id)).findFirst();
        if(paste.isPresent()){
            paste.get().setTitle(pasteEntry.getTitle());
            paste.get().setContent(pasteEntry.getContent());
          pasteServices.savePaste(paste.get());
          return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{id}")
    public ResponseEntity<?> DeletePaste(@PathVariable String id){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userServices.findByuserName(userName);

        if(user.getPasteEntries().isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<PasteEntry> paste=user.getPasteEntries().stream().filter(x->x.get_id().equals(id)).findFirst();
        if(paste.isPresent()){
            pasteServices.deletePaste(paste.get().get_id(),userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
