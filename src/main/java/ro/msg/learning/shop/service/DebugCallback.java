package ro.msg.learning.shop.service;

import org.apache.olingo.odata2.api.ODataDebugCallback;

public class DebugCallback implements ODataDebugCallback {
    @Override
    public boolean isDebugEnabled() {
        return true;
    }
}