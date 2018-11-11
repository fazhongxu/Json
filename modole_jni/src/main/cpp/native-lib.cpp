#include <jni.h>
#include <string>


void change(int &a,int &b) {
    int temp = a;
    a = b;
    b = temp;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_xxl_modulejni_MainActivity_stringFromJNI(
        JNIEnv *env, jobject instance) {

    std::string hello = "Hello from C++";

    int i = 100;
    int* p = &i;

    printf("i的地址为%d",*p);

    int a = 100,b = 300;

    change(a,b);

    return env->NewStringUTF(hello.c_str());
}

