package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ads;

import java.util.ArrayList;

@Data
public class ResponseWrapperAdsDto {
    private int count;
    private ArrayList <Ads> results;

}
