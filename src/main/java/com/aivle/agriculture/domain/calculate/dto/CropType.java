package com.aivle.agriculture.domain.calculate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CropType {
    SWEET_POTATO("고구마"),         // 고구마
    ONION("양파"),                // 양파
    WATERMELON("수박"),           // 수박
    SESAME("참깨"),               // 참깨
    SOYBEAN("콩"),              // 콩
    RED_BEAN("팥"),             // 팥
    CABBAGE("배추"),              // 배추
    GARLIC("마늘"),               // 마늘
    TEA("차"),                  // 차
    AUTUMN_SQUASH("단호박"),          // 단호박
    RICE("벼"),                 // 벼
    WHEAT("밀"),                // 밀
    BARLEY("보리"),               // 보리
    OAT("귀리"),                  // 귀리
    CORN("옥수수"),                 // 옥수수
    POTATO("감자"),               // 감자
    CHESTNUT("밤"),             // 밤
    PEACH("복숭아"),                // 복숭아
    PLUM("자두"),                 // 자두
    MAESIL("매실"),               // 매실
    OMIJA("오미자"),                // 오미자
    YUZU("유자"),                 // 유자
    WALNUT("호두"),               // 호두
    APRICOT("살구"),              // 살구
    CITRUS("감귤(온주밀감류)"),               // 감귤(온주밀감류)
    JUJUBE("대추"),               // 대추
    GRAPE("포도"),                // 포도
    KIWI("참다래"),                 // 참다래
    PEPPER("고추"),               // 고추
    BROCCOLI("브로콜리"),             // 브로콜리
    CARROT("당근"),               // 당근
    RADISH("무"),               // 무
    SPINACH("시금치"),              // 시금치
    LETTUCE("양상추"),              // 양상추
    BUCKWHEAT("메밀"),            // 메밀
    STRAWBERRY("딸기"),           // 딸기
    TOMATO("토마토"),               // 토마토
    CUCUMBER("오이"),             // 오이
    KOREAN_MELON("참외"),               // 참외
    PUMPKIN("호박"),              // 호박
    PAPRIKA("파프리카"),              // 파프리카
    CHRYSANTHEMUM("국화"),        // 국화
    MELON("멜론"),                // 멜론
    EGGPLANT("가지"),             // 가지
    GREEN_ONION("대파"),          // 대파
    SCALLION("쪽파(실파)"),             // 쪽파(실파)
    LILY("백합"),                 // 백합
    CARNATION("카네이션"),            // 카네이션
    WATER_PARSLEY("미나리"),        // 미나리
    CROWN_DAISY("쑥갓"),          // 쑥갓
    LOG_SHIITAKE("원목재배 표고버섯"),         // 원목재배 표고버섯
    OYSTER_MUSHROOM("느타리버섯"),      // 느타리버섯
    KING_OYSTER_MUSHROOM("새송이버섯"), // 새송이버섯
    BUTTON_MUSHROOM("양송이버섯"),      // 양송이버섯
    CHIVE("부추"),                // 부추
    GINSENG("인삼"),              // 인삼
    MULBERRY("오디"),             // 오디
    DUREUP("두릅"),                // 두릅
    BLUEBERRY("블루베리"),            // 블루베리
    BOKBUNJA("복분자"),             // 복분자
    FIG("무화과");                // 무화과

    private final String koreanName;

    CropType(String koreanName) {
        this.koreanName = koreanName;
    }

    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    @JsonCreator
    public static CropType fromKoreanName(String koreanName) {
        for (CropType type : CropType.values()) {
            if (type.koreanName.equals(koreanName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("해당 한글명에 해당하는 작물 타입이 없습니다: " + koreanName);
    }
}
