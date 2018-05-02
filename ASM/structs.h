#include <stdio.h>

#ifndef ASM_STRUCTS
#pragma once

#define NUM_OF_COMMANDS 9
#define NULL_SYMBOL_CHECK 0
#define COLON_CHECK 1
#define LABEL_POSITION 0

#define SUCCESS_CODE 1
#define STOP_CODE 0
#define NOT_FOUND_LABEL -1

#define MEM_SIZE 262143
#define STACK_SIZE 30
#define HEAP_SIZE 100

#define TRUE 1
#define FALSE 0

union {
    int i;
    char c[sizeof(int)];
}memory_unit;


typedef struct file {
    int command_num; // counting begins with one
    char * code_file;
    FILE * file;
} my_file;

typedef struct info {
    int stack_pointer; //points first free element in stack
    int heap_pointer;  //points first free element in heap ?

    int * mem;
    int * stack;
} info;

typedef int (*func_pointers)(info *, char *, my_file *);

typedef struct fun_pointers {
    char * name;
    int (*func)(info *, char *, my_file *);
} functions;

functions ** pFunctions;
#endif
