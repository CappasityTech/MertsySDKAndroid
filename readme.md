# Mertsy SDK for Android. Sample.

The Mertsy SDK allows you to integrate 3D/360 product imaging and capturing functionality into your application. It consists of several modules. Some modules may not be available to you, check your license restrictions.

<img src="https://cappasity.com/wp-content/uploads/2022/07/MertsySDK-scaled.jpg" alt="Mertsy SDK" style="width:1000px;"/>

Instruction:

1. Download mertsy-sdk.aar and save it to the folder 'frameworks'.
2. To initialize SDK, use your Access Token in the init method.

Kotlin:

  class App : Application() {

      override fun onCreate() {
          super.onCreate()
          MertsySDK.init(this, "YOUR TOKEN HERE")
      }
  }
  
Java:

  public class App extends Application {

      @Override
      public void onCreate() {
          super.onCreate();
          MertsySDK.init(this, "YOUR TOKEN HERE");
      }
  }

3. Launch the app. Select the capturing mode (Panorama, Cars or Indoor) and follow the prompts.
4. When the capturing is completed, a link to the new model will be copied to the clipboard.
5. Select the 'View model' mode and in the text field paste the modelID, SKU/VIN of the model or a link (3d.cappasity.com/u/...) to the model. Click Search.

# Contact us

SDK access: support@cappasity.com
Website - https://cappasity.com/contact/

Copyright (c) 2022, Cappasity Inc. All rights reserved.

Neither the name of Cappasity Inc., nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.



