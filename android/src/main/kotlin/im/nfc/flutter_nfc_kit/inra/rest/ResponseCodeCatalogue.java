package im.nfc.flutter_nfc_kit.inra.rest;

public enum ResponseCodeCatalogue {
    OPERATION_OK(0),
    APPVERSION_OK(1),
    CARD_PENDING_OPERATION(3),
    CARD_CHECKED(4),
    LOAD_PRODUCT_CHECKED(5),
    LOAD_PRODUCT_WRITTEN(6),
    LOAD_PRODUCT_COMMITTED(7),
    OPERATION_OK_INCOMPLETE_COMMIT(8),
    OPERATION_OK_INCOMPLETE_WRITE(9),
    ERROR_COMMAND_NOT_EXECUTED(-1),
    INCORRECT_PARAM(-2),
    UNHANDLED_STATUS(-3),
    ERROR_CRC(-4),
    ERROR_FRAUD(-6),
    ERROR_BLACK_LIST(-7),
    ERROR_FEES_EXPIRED(-8),
    NOT_ALL_OPERATIONS_COMPLETED(-9),
    AUTHENTICATION_ERROR(-10),
    READ_WRITE_ERROR(-11),
    ERROR_SEQUENCE(-12),
    ERROR_UID(-13),
    CARD_REMOVED_FROM_ANTENNA(-14),
    ERROR_CARD_NOT_ACTIVE(-15),
    ERROR_CONTRACT_NOT_ACTIVE(-16),
    ERROR_DISABLED_CARD(-17),
    ERROR_BLOCKED_CARD(-18),
    ERROR_SALE_NOT_ALLOWED(-19),
    ERROR_CONTRACT_TYPE_NOT_IN_CONFIGURATION(-20),
    ERROR_DIFFERENT_CARD_FORMAT_VERSION(-21),
    ERROR_CARD_CONTRACT_PROFILE(-22),
    ERROR_PROFILE_START_DATE(-23),
    ERROR_PROFILE_END_DATE(-24),
    ERROR_CONTRACT_SLOT_NOT_AVAILABLE(-25),
    ERROR_CARD_UPDATING_NOT_STARTED(-26),
    ERROR_CARD_CORRUPTED(-27),
    ERROR_COMMIT_PENDING(-28),
    ERROR_COMMIT_ALREADY_DONE(-29),
    ERROR_INCOMPLETE_TRANSACTION_TO_BE_FIXED(-30),
    ERROR_SSR_INFRASTRUCTURE(-40),
    ERROR_GENERIC(-41),
    ERROR_CARDDATA_NOTFOUND(-42),
    ERROR_TRANSACTION_NOT_EXISTS(-43),
    ERROR_CARD_WRITING_EXPIRED_SAM(-44),
    ERROR_APPVERSION(-45),
    ERROR_LANGUAGE_NOT_SUPPORTED(-46),
    ERROR_SWITCH_COMMUNICATION(-47),
    ERROR_NOT_INITIALIZATION_DATA(-48),
    CARD_WITHOUT_RA(-49),
    ERROR_TRANSACTION_IN_USE(-50),
    CARD_MAX_SALDO(-51),
    AUTHENTICATION_ERROR_INOPERATION(-52),
    DEVICE_REMOVED(-53),
    ERROR_SWITCH_LOAD_MANDATE_ERROR(-54),
    ERROR_SWITCH_LOAD_RA_ERROR(-55),
    CARD_BLOCKED(-56),
    CARD_DESTROYED(-57),
    CONTRACT_BLOCKED(-58),
    ERROR_REQUEST_NOT_COHERENTE(-59),
    ERROR_SAM_PROBLEM(-60),
    ERROR_HTTP_INCORRECT_PARAM(-61);

    private int value;

    private ResponseCodeCatalogue(int value) {
        this.value = value;
    }

    public static ResponseCodeCatalogue getResponseCodeName(int code) {
        ResponseCodeCatalogue[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            ResponseCodeCatalogue responseCodeCatalogue = var4[var2];
            if (code == responseCodeCatalogue.value) {
                return responseCodeCatalogue;
            }
        }

        return null;
    }

    public int getValue() {
        return this.value;
    }
}