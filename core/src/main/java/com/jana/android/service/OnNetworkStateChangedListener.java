/**
 *
 */
package com.jana.android.service;

import android.net.NetworkInfo;

/**
 * @author islam
 */
public interface OnNetworkStateChangedListener {

    void onChange(int type, NetworkInfo networkInfo);

}
