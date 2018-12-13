package com.iqb.asset.inst.platform.common.exception.pledge;


public class PledgeInvalidException extends Exception{
    public static final String  ERROR_FORMAT = " ServiceCode[%s] Reason [%s] Layer [%s] Location [%s] Exception [%s]";
    
    private static final long serialVersionUID = 1L;
    private Reason reason;
    private Location location;
    private Layer layer;

    public static enum Reason {
        INVALID_REQUEST_PARAMS, DB_NOT_FOUND, INVALID_ENTITY, UNKNOW_TYPE, DB_ERROR, UNKNOWN_ERROR, REPEATED_INJECTION, ACCOUNT_NOT_LOGIN,
        WF_RISK_STATUS_FAIL, TYPE_PARSE_ERROR
    }

    public static enum Layer {
        CONTROLLER, SERVICE, MANAGER, REPOSITORY
    }

    public static enum Location {
        A, B, C, D, E
    }

    public PledgeInvalidException(Reason reason, Layer layer,
            Location location) {
        this.reason = reason;
        this.location = location;
        this.layer = layer;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
