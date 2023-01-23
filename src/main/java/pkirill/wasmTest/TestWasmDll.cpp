extern "C" {
  __attribute__((visibility("default"))) int callToCpp();
  __attribute__((visibility("default"))) char const * getC8String();
  __attribute__((visibility("default"))) char16_t const * getC16String();
  __attribute__((visibility("default"))) float const * getCFloatArray8();
}

extern "C" {
  extern int jsFunction(int a, int b);
}

int callToCpp() {
  return jsFunction(22, 33);
}

char const * getC8String() {
  return "this is a utf string from C/C++";
}

char16_t const * getC16String() {
  return u"this is a char16_t string 新年快乐 from C/C++";
}

static float fp32Matrix8[8] = { 1.11, 2.22, 3.33, 4.44, 5.55, 6.66, 7.77, 8.88 };

float const * getCFloatArray8() {
  return fp32Matrix8;
}