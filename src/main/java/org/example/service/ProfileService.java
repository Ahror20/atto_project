package org.example.service;

import lombok.Setter;
import org.example.dto.Profile;
import org.example.enums.GeneralStatus;
import org.example.enums.ProfileRole;
import org.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public void profileList() {
        List<Profile> profileList = profileRepository.getProfileList();
        for (Profile profile : profileList) {
            if (profile != null && !profile.getRole().equals(ProfileRole.ADMIN)) {
                System.out.println(profile);
            }
        }
    }

    public void changeProfileStatus(String phone) {
        Profile profile = profileRepository.getProfileByPhone(phone);
        if (profile == null) {
            System.out.println("Profile not found");
            return;
        }
      int n;
        if (profile.getStatus().equals(GeneralStatus.ACTIVE)) {
           n= profileRepository.changeProfileStatus(phone, GeneralStatus.BLOCK);
        } else {
           n= profileRepository.changeProfileStatus(phone, GeneralStatus.ACTIVE);
        }

        if (n==1){
            System.out.println("your card status changed");
        }


    }

}
