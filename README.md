[![Build Status](https://travis-ci.org/mmarcon/androidutils.png?branch=master)](https://travis-ci.org/mmarcon/androidutils)

# Android Utils

A collection of utility classes for Java/Android development, crafted by a big JavaScript/Node.js supporter.

## Collections

### `MFArrayList`

In JavaScript arrays have a bunch of convenience methods to perform operations on the elements of the array. The functions that I use more often and I really missed when I went back to doing Java where `map` and `filter`.

`MFArrayList` extends Java's `ArrayList` and adds support for such operations.

## Async

Very often, when I work with Node.JS I find very convenient to rely on [node-async](https://github.com/caolan/async) to execute a number of tasks asynchronously in series or in parallel. I thought it'd be useful to have the same for Java.

This implementation uses a *cached thread pool* by default, however it is possible to create a custom instance of the class that uses any other `ThreadPoolExecutor` subclass.
