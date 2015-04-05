/*
 * Copyright 2015 Christian Bahl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.christianbahl.appkit.viewstate.data.interfaces;

import android.os.Bundle;
import com.christianbahl.appkit.view.CBView;

/**
 * @author Christian Bahl
 */
public interface CBViewStateRestoreableInterface<V extends CBView> extends CBViewStateInterface<V> {

  void saveInstanceState(Bundle outState);

  boolean restoreInstanceState(Bundle savedInstanceState);
}