/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
#ifndef LA_LOG_H
#define LA_LOG_H

#ifdef LA_ANDROID
#include <android/log.h>
#endif /* LA_ANDROID */

/* Setup LOG_TAG using macro BS */
#ifndef NDEBUG
#define LOG_TAG __PRETTY_FUNCTION__
#else /* using LA_LOG_TAG */
#define LA_QUOTE(NAME) #NAME
#define LA_NAME(NAME) LA_QUOTE(NAME)
#define LOG_TAG LA_NAME(LA_LOG_TAG)
#endif /* NDEBUG */

/* Specify the log codes */
#ifdef LA_ANDROID
/* With the same ones as Android */
#define LA_LOG_VERBOSE ANDROID_LOG_VERBOSE
#define LA_LOG_DEBUG   ANDROID_LOG_DEBUG
#define LA_LOG_INFO    ANDROID_LOG_INFO
#define LA_LOG_WARN    ANDROID_LOG_WARN
#define LA_LOG_ERROR   ANDROID_LOG_ERROR
#define LA_LOG_FATAL   ANDROID_LOG_FATAL
#else /* !LA_ANDROID */
/* With our own values*/
#define LA_LOG_VERBOSE 0x00
#define LA_LOG_DEBUG   0x01
#define LA_LOG_INFO    0x02
#define LA_LOG_WARN    0x04
#define LA_LOG_ERROR   0x08
#define LA_LOG_FATAL   0xFF
#endif /* LA_ANDROID */

/**
 * TODO neaten this up! Also, my eyes... still yet unfinished, write:
 * __la_log_assert(int, const char *, const char *, ...) // and then, below:
 * #define LOGA(COND,...) __XXX_log_assert(COND,LA_LOG_TAG,__VA_ARGS__)
 */
void __la_log_print(int, const char *, const char *, ...);

/* Define general LOG macros */
#ifdef LA_ANDROID
#define LOG(LEVEL,...) __android_log_print(LEVEL,LOG_TAG,__VA_ARGS__)
#else /* !LA_ANDROID */
#define LOG(LEVEL,...) __la_log_print(LEVEL,LOG_TAG,__VA_ARGS__)
#endif /* LA_ANDROID */

/* Aliases for different log-levels */
#define LOGV(...) LOG(LA_LOG_VERBOSE, __VA_ARGS__)
#define LOGD(...) LOG(LA_LOG_DEBUG,   __VA_ARGS__)
#define LOGI(...) LOG(LA_LOG_INFO,    __VA_ARGS__)
#define LOGW(...) LOG(LA_LOG_WARN,    __VA_ARGS__)
#define LOGE(...) LOG(LA_LOG_ERROR,   __VA_ARGS__)
#define LOGF(...) LOG(LA_LOG_FATAL,   __VA_ARGS__)

#endif /* LA_LOG_H */
