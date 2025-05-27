package com.pasteApp.paste.Services;

import com.pasteApp.paste.Entity.PasteEntry;
import com.pasteApp.paste.Entity.User;
import com.pasteApp.paste.Repository.PasteRepo;
import com.pasteApp.paste.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PasteServices {
    @Autowired
    private PasteRepo pasteRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserServices userServices;
    public void savePaste(PasteEntry pasteEntry,String userName){
        try {
            User user=userRepo.findByUserName(userName);
            PasteEntry saved=pasteRepo.save(pasteEntry);
            user.getPasteEntries().add(saved);
            userRepo.save(user);
        }
        catch (Exception e){
            throw new RuntimeException("An error occured while saving the entry"+e);
        }
    }

    public void savePaste(PasteEntry pasteEntry){
        pasteRepo.save(pasteEntry);
    }

    public Optional<PasteEntry> findPasteById(String id){
        return pasteRepo.findById(id);
    }

    public void deletePaste(String id,String userName){
        boolean removed=false;
        try{
            User user=userServices.findByuserName(userName);
            removed=user.getPasteEntries().removeIf(x->x.get_id().equals(id));
            if(removed){
                userServices.removePasteRef(user);
                pasteRepo.deleteById(id);
            }
        }
        catch (Exception e){
            throw new RuntimeException("An error occured while deleting the pastes "+ e);
        }

    }
}


