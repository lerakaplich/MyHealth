#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct {
    jlong id;
    char name[50];
    char password[50];
} User;

User users[100];
int userCount = 0;

JNIEXPORT void JNICALL
Java_com_kaplich_myhealth_NativeLib_initRandom(JNIEnv* env, jobject obj) {
    srand(time(NULL));  // Инициализация генератора случайных чисел
}

JNIEXPORT jlong JNICALL
Java_com_kaplich_myhealth_NativeLib_registerUser(JNIEnv* env, jobject obj, jstring name, jstring password) {
    if (userCount >= 100) {
        return -1; // Достигнуто максимальное количество пользователей
    }

    jlong userId = rand() % 90000000 + 10000000; // Генерация случайного ID

    const char* nameStr = (*env)->GetStringUTFChars(env, name, 0);
    const char* passwordStr = (*env)->GetStringUTFChars(env, password, 0);

    // Сохранение данных пользователя
    users[userCount].id = userId;
    strcpy(users[userCount].name, nameStr);
    strcpy(users[userCount].password, passwordStr);
    userCount++;

    (*env)->ReleaseStringUTFChars(env, name, nameStr);
    (*env)->ReleaseStringUTFChars(env, password, passwordStr);

    return userId; // Возврат сгенерированного ID
}

JNIEXPORT jstring JNICALL
Java_com_kaplich_myhealth_NativeLib_getUserName(JNIEnv* env, jobject obj, jlong userId) {
    for (int i = 0; i < userCount; i++) {
        if (users[i].id == userId) {
            return (*env)->NewStringUTF(env, users[i].name);
        }
    }
    return NULL; // Если пользователь не найден
}
