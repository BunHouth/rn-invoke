# React Native Invoke

[![NPM Version](https://img.shields.io/npm/v/rn-invoke.svg?style=flat)](https://www.npmjs.com/package/rn-invoke)
[![NPM Downloads](https://img.shields.io/npm/dm/rn-invoke.svg?style=flat)](https://www.npmjs.com/package/rn-invoke)
[![Build Status](https://travis-ci.org/wix/rn-invoke.svg?branch=master)](https://travis-ci.org/wix/rn-invoke)


Invoke any native code directly from Javascript in React Native (without wrapping it first with a native manager). Gives you full access from JS to all native API of iOS and Android.
## Why
The story behind this library and why it might be useful:<br>
https://medium.com/@talkol/invoke-any-native-api-directly-from-pure-javascript-in-react-native-1fb6afcdf57d
<br><br>
## Install

####Both Platforms
- In your project root, `npm install rn-invoke --save`<br>
or add it in your `package.json`:
 
```json
  "dependencies": {
	...
    "rn-invoke": "^0.2.0"
 }
```

#### iOS

* In your project root, `npm install rn-invoke --save`

* In Xcode, in Project Navigator (left pane), right-click on the `Libraries` > `Add files to [project name]` <br> Add `./node_modules/rn-invoke/ios/RNInvoke.xcodeproj`

* In Xcode, in Project Navigator (left pane), click on your project (top) and select the `Build Phases` tab (right pane) <br> In the `Link Binary With Libraries` section add `libRNInvoke.a` 

* In Xcode, in Project Navigator (left pane), click on your project (top) and select the `Build Settings` tab (right pane) <br> In the `Header Search Paths` section add `$(SRCROOT)/../node_modules/rn-invoke/ios` <br> Make sure on the right to mark this new path `recursive`

#### Android

- Add `rn-invoke` from `node_modules` to your `settings.gradle`:

	```gradle
	include ':app'
	include ':rn-invoke'
	project(':rn-invoke').projectDir = new File(rootProject.projectDir, '../node_modules/rn-invoke/android/invoke')
	```

- In `app/build.gradle` add `rn-invoke` as a dependency
	
	```gradle
	dependencies {
		...
	    compile project(':rn-invoke')
	}
```

- In `YourApplication.java` register `InvokeReactPackage` in your pacakges:

	```java
	@Override
	protected List<ReactPackage> getPackages() {
	    return Arrays.<ReactPackage>asList(
	            new MainReactPackage(),
	            ...
	            new InvokeReactPackage()
	    );
	 }
	```

<br><br>
## Executing calls to native

> Notice that this is regular Javascript code. It has full access to all native API in iOS and there's no RN native manager involved wrapping each individual API call.

```js
import Invoke from 'rn-invoke';

// execute a single call
const _getContentOffset = Invoke.call(_scrollView, 'contentOffset');
const {x, y} = await Invoke.execute(_getContentOffset);
```

> Invoke.execute returns a promise. The native code doesn't actually execute until Invoke.execute runs.

```js
// execute multiple calls
const _getScrollView = Invoke.call(_scrollParent, 'scrollView');
const _getContentOffset = Invoke.call(_getScrollView, 'contentOffset');
const {x, y} = await Invoke.execute(_getContentOffset);
```

> Only simple serializable objects can pass between native and JS. Since many methods take a complex object as argument, we support making multiple calls in one execution so the result of one call can be passed to the next one without going through JS.
<br><br>

## Example invocations

###iOS
###### 1. from Objective-C

```objc
CGPoint offset = [componentView.scrollView contentOffset];
```

###### &nbsp;&nbsp;&nbsp; to Javascript

```js
const _getScrollView = Invoke.call(_componentView, 'scrollView');
const _getOffset = Invoke.call(_getScrollView, 'contentOffset');
const {x, y} = await Invoke.execute(_getOffset);
```
<br>
###### 2. from Objective-C

```objc
CGRect frame = componentView.frame;
```

###### &nbsp;&nbsp;&nbsp; to Javascript

```js
const _getFrame = Invoke.call(_componentView, 'frame');
let {x, y, width, height} = await Invoke.execute(_getFrame);
```
<br>
###### 3. from Objective-C

```objc
[componentView setFrame:frame];
```

###### &nbsp;&nbsp;&nbsp; to Javascript

```js
const _setFrame = Invoke.call(_componentView, 'setFrame:', Invoke.IOS.CGRect({x, y, width, height}));
await Invoke.execute(_setFrame);
```
<br>
###### 4. from Objective-C

```objc
id textView = [componentView valueForKey:@'_textView'];
CGRect pos = [textView caretRectForPosition:textView.selectedTextRange.start];
```

###### &nbsp;&nbsp;&nbsp; to Javascript

```js
const _getTextView = Invoke.call(_componentView, 'valueForKey:', '_textView');
const _getSelectedTextRange = Invoke.call(_getTextView, 'selectedTextRange');
const _getStartPosition = Invoke.call(_getSelectedTextRange, 'start');
const _getCaretRect = Invoke.call(_getTextView, 'caretRectForPosition:', _getStartPosition);
const {x, y, width, height} = await Invoke.execute(_getCaretRect);
```

###Android
###### 1. from java
```java
reactSwipeRefreshLayout.setRefreshing(false);
```
###### &nbsp;&nbsp;&nbsp; to Javascript
```js
const swipeRefreshLayout = Invoke.React.view(this.refs['refresh']);
const setRefreshing = Invoke.call(swipeRefreshLayout, 'setRefreshing', {type: "Boolean", value: false});
await Invoke.execute(setRefreshing);
```

###### 2. from java
```java
scrollView.getScrollY()
```
###### &nbsp;&nbsp;&nbsp; to Javascript
```js
const scrollView = Invoke.React.view(this.refs['scroll']);
const getScrollY = Invoke.call(scrollView, 'getScrollY');
const y = await Invoke.execute(getScrollY);
```

###### 3. from java
```java
textView.getSelectionEnd()
```
###### &nbsp;&nbsp;&nbsp; to Javascript
```js
const textView = Invoke.React.view(this.refs['input']);
const getSelectionEnd = Invoke.call(textView, 'getSelectionEnd');
const selectionEnd = await Invoke.execute(getSelectionEnd);
```

<br>
## Full example project
Available [here](example)

####iOS

```sh
cd example
npm install
react-native run-ios
```


or `open ios/example.xcodeproj` to open in xcode.

####Android
```sh
cd example
npm install
react-native run-android
```


####javascript
* [Example of getting the scroll offset of a ScrollView](example/src/scroll-offset-example.js)
* [Example of getting, changing and setting the frame of RefreshControl](example/src/refresh-control-pos-example.js)
* [Example of getting the cursor pos from a TextInput](example/src/text-cursor-pos-example.js)
<br><br>

## API

##### > `Invoke.execute(invocation)`

Send the entire invocation to native and execute it. Code runs in native only when we reach this command. Returns a promise with the final return value (make sure it's serializable).
<br><br>
##### > `Invoke.call(target, methodSignature, arg1, arg2, ...)`

Prepare a call for later execution.
<br><br>
##### > `Invoke.React.view(componentRef)`

Returns (in later execution) the native view backing the React component ref.<br>Example:
```js
<ScrollView refreshControl={<RefreshControl refreshing={true} ref='myRefreshControl'/>} />
const _componentView = Invoke.React.view(this.refs['myRefreshControl']);
```
<br>
##### > `Invoke.IOS.CGPoint({x, y})`

Returns (in later execution) an iOS point.
<br><br>
##### > `Invoke.IOS.CGRect({x, y, width, height})`

Returns (in later execution) an iOS rect.
<br><br>
## Notes

* The final return value from native arrives as the promise result of `Invoke.execute`. It has to be serializable! If you have return values that aren't serializable (like complex objects), you probably need to have several `Invoke.call`s and pass them between eachother.

* All native code is executed on the main thread.

<br>
## License

MIT

