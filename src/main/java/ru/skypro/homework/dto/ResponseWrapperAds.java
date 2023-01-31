package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ads;

@Data
public class ResponseWrapperAds {
    private int count;
    private Ads[] results;

}
