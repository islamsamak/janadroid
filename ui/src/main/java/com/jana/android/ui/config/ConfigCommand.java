/**
 *
 */
package com.jana.android.ui.config;

import android.view.View;

/**
 * @author islamsamak
 */
public interface ConfigCommand {

    void loadConfig();

    void apply(View root);
}
