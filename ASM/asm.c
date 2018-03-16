#include <stdio.h>
#include <malloc.h>
#include <string.h>
#include <stdlib.h>
#include "structs.h"


//------------------
int ldc(info *, char *, my_file *);
int add(info *, char *, my_file *);
int ret(info *, char *, my_file *);
int jmp(info *, char *, my_file *);
int br(info *, char *, my_file *);
int st(info *, char *, my_file *);
int cmp(info *, char *, my_file *);
int ld(info *, char *, my_file *);
int sub(info *, char *, my_file *);
//------------------

info * init_info() {
    int * mem = calloc(sizeof(int), MEM_SIZE);
    int * stack = calloc(sizeof(int), STACK_SIZE + HEAP_SIZE);
    info * info = calloc(sizeof(info), 1);

    if (mem && stack && info) {
        info->mem = mem;
        info->stack = stack;
        info->stack_pointer = 0;
        info->heap_pointer = STACK_SIZE + HEAP_SIZE - 1;
    } else {
        printf("Err while memory and stack creating!\n");
    }

}

my_file * init_file(char * code_file) {

    FILE * file = fopen(code_file, "r");

    if (file == NULL) {
        printf("Can't open file!\n");
        return NULL;
    } else {
        my_file * my_file = calloc(sizeof(my_file), 1);

        if (my_file != NULL) {
            my_file->command_num = 0;
            my_file->code_file = code_file;
            my_file->file = file;
            return my_file;
        } else {
            printf("Error while file creating\n");
            return NULL;
        }
    }


}

functions * init_functions() {
    functions * functions = calloc(sizeof(functions[0])* NUM_OF_COMMANDS, 1);

    char * str[] = {"add", "ldc", "ret", "jmp", "br", "st", "cmp", "ld", "sub"};
    func_pointers methods[] = {add, ldc, ret, jmp, br, st, cmp, ld, sub};

    if (functions != NULL) {

        for (int i = 0; i < NUM_OF_COMMANDS; ++i) {
            functions[i]->name = str[i];
            functions[i]->func = methods[i];
        }

        return functions;
    } else {
        printf("Err while fun creating!\n");
        return NULL;
    }
}

int run_func(functions * funcs, char * word, info * info, my_file * my_file) {

    if (info->stack_pointer >= STACK_SIZE) {
        printf("Stack overflow!\n");
        return STOP_CODE;
    }

    for (int i = 0; i < NUM_OF_COMMANDS; ++i) {

        if (!strcmp(funcs[i]->name, word)) {
            my_file->command_num += 1;
            return funcs[i]->func(info, strtok(NULL, " "), my_file);
        }
    }

    printf("Function <%s> not found! line - %d\n", word, my_file->command_num);
    return STOP_CODE;
}

int find_label(info * info, char  label) {

    for (int i = info->heap_pointer; i < STACK_SIZE + HEAP_SIZE; ++i) {
        if ((char) info->stack[i] == label) {
            return i;
        }
    }

    return NOT_FOUND_LABEL;
}

int parse_command(functions * funcs, char * command, info * info, my_file * myFile, int isFromJmp) {
    int code = SUCCESS_CODE;

    if (command[COLON_CHECK] != ':') {
        if (command[NULL_SYMBOL_CHECK] != ';')  {
            char * words = strtok(command, " \n");
            code = run_func(funcs, words, info, myFile);
        } else {
            printf("-------------------------------------------------\n");
            myFile->command_num += 1;
            printf("comment%s", command);
            printf("-------------------------------------------------\n");
        }

    } else {

        if (isFromJmp == TRUE) {
            free(funcs);
            free(command);
        } else {
            if (find_label(info, command[LABEL_POSITION]) == NOT_FOUND_LABEL) {

                memory_unit.i = myFile->command_num + 1;
                memory_unit.i = memory_unit.i << sizeof(char) * 8;
                memory_unit.c[0] = command[LABEL_POSITION];

                info->stack[info->heap_pointer] =  memory_unit.i;

                info->heap_pointer -= 1;

                if (info->heap_pointer < STACK_SIZE) {
                    printf("Warning to much labels!\n");
                }

            }
        }


        strtok(command, " \n");
        code = run_func(funcs, strtok(NULL, " \n"), info, myFile);
    }
    return code;
}

void free_info(info * info) {
    free(info->stack);
    free(info->mem);
    free(info);
}

void start_program(char * code_file) {
    printf("****************START PROGRAM!!!*****************\n");

    functions * func = init_functions();
    info * info = init_info();

    my_file * mf = init_file(code_file);

    char * line = calloc(sizeof(char), 255);

    if (line != NULL) {

        int is_finished = 1;

        while (is_finished) {

            fgets(line, 200, mf->file);
            is_finished = parse_command(func, line, info, mf, FALSE);
            if (!is_finished) {
                printf("***********STOPPED IN LINE NUMBER: %d************\n", mf->command_num);
            }
        }
    }

    free(line);
    free(func);
    free_info(info);
    fclose(mf->file);
    free(mf);
}

int get_line_number(int label) {
    memory_unit.i = label;
    memory_unit.i = memory_unit.i >> 8;
    memory_unit.c[3] = 0;

    return memory_unit.i;
}

int ldc(info * info, char * arg, my_file * file) {
    int num = atoi(arg);

    info->stack[info->stack_pointer] = num;
    info->stack_pointer += 1;

    printf("Success putting -  %d\n", info->stack[info->stack_pointer - 1]);
    return SUCCESS_CODE;

}

int add(info * info, char * arg, my_file * file) {
    printf("%d + %d\n", info->stack[info->stack_pointer - 1], info->stack[info->stack_pointer - 2]);

    if (info->stack_pointer > 1) {
        info->stack[info->stack_pointer - 2] = info->stack[info->stack_pointer - 1]
                                               + info->stack[info->stack_pointer - 2];

        info->stack_pointer -= 1;

        printf("addition result %d\n", info->stack[info->stack_pointer - 1]);
        return SUCCESS_CODE;
    } else {
        printf("Addition error!");
        return STOP_CODE;
    }
}

int ret(info * info, char * arg, my_file * file) {
    printf("Success finished!\n");
    return STOP_CODE;
}

int ld(info * info, char * arg, my_file * file) {
    int num = atoi(arg);

    if (num != NULL) {
        info->stack[info->stack_pointer] = info->mem[num];
        info->stack_pointer += 1;
        printf("load from mem - %d\n", info->stack[info->stack_pointer - 1]);
        return SUCCESS_CODE;
    } else {
        printf("ld wrong arg!");
        return STOP_CODE;
    }
}

int st(info * info, char * arg, my_file * file) {
    int num = atoi(arg);

    if (num != NULL) {
        info->mem[num] = info->stack[info->stack_pointer - 1];
        info->stack_pointer -= 1;
        printf("Mem writing success - %d adr - %d!\n", info->mem[num], num);
        return SUCCESS_CODE;
    } else {
        printf("st wrong arg!\n");
        return STOP_CODE;
    }
}

int sub(info * info, char * arg, my_file * file) {
    if (info->stack_pointer > 1) {
        info->stack[info->stack_pointer - 2] = info->stack[info->stack_pointer - 1]
                                               - info->stack[info->stack_pointer - 2];

        info->stack_pointer -= 1;

        printf("deduction result %d\n", info->stack[info->stack_pointer - 1]);
        return SUCCESS_CODE;
    } else {
        printf("Deduction error!");
        return STOP_CODE;
    }
}

int cmp(info * info, char * arg, my_file * file) {

    if (info->stack_pointer > 1) {

        int a = info->stack[info->stack_pointer - 1];
        int b = info->stack[info->stack_pointer - 2];

        if (a < b) {
            info->stack[info->stack_pointer - 2] = -1;
            info->stack_pointer -= 1;
            printf("%d < %d => -1\n", a, b);
            return SUCCESS_CODE;
        } else if (a > b){
            info->stack[info->stack_pointer - 2] = 1;
            info->stack_pointer -= 1;
            printf("%d > %d\n => 1\n", a, b);
            return SUCCESS_CODE;
        } else {
            info->stack[info->stack_pointer - 2] = 0;
            info->stack_pointer -= 1;
            printf("%d = %d\n => 0\n", a, b);
            return SUCCESS_CODE;
        }

    } else {
        printf("Comparison error!");
        return STOP_CODE;
    }
}

int jmp(info * info, char * arg, my_file * my_file) {

    int i = find_label(info, arg[0]);

    char * line = calloc(sizeof(char), 255);

    if (line != NULL) {

        if (i != NOT_FOUND_LABEL) {

            int number_of_line = get_line_number(info->stack[i]);

            fclose(my_file->file);
            FILE * file = fopen(my_file->code_file, "r");

            if (file != NULL) {
                my_file->file = file;
                my_file->command_num = number_of_line - 1;

                for (int j = 0; j < number_of_line - 1; ++j) {
                    fgets(line, 200, my_file->file);
                }

                free(line);
            } else {
                free(line);
                printf("Can't open file!");
                return STOP_CODE;
            }

        } else {
            while (line[LABEL_POSITION] != arg[0] || line[COLON_CHECK] != ':') {
                my_file->command_num += 1;
                if (fgets(line, 200, my_file->file) == NULL) {
                    printf("Can't find label");
                    free(line);
                    return STOP_CODE;
                }
            }

            return parse_command(init_functions(), line, info, my_file, TRUE);
        }
    } else {
        printf("Can't create buff!");
    }
    return SUCCESS_CODE;
}

int br(info * info, char * arg, my_file * my_file) {

    if (info->stack[info->stack_pointer - 1] != 0) {
        printf("start to jump!\n");
        return jmp(info, arg, my_file);
    }

    printf("Not jump!\n");
    return SUCCESS_CODE;
}
