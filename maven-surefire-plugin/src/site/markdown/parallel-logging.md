<!--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

Safe use of Console and STDOUT STDERR in Parallel Tests
========================

If you log to standard output, error or console from `parallel` tests you may have noticed
the log messages become mixed up.

To come over this issue, especially on Ubuntu/Linux, synchronize the `PrintStream` object.
You can freely create a `ReportUtility` class embedding this mechanism, but in principle
the synchronization becomes as follows:

```
    @Test
    public void shouldRunTest() {
        synchronized (System.out) {
            System.out.println(message);
        }
        
        ...
    }
```
