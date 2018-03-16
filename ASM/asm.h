#include <memory.h>
#include "structs.h"

#ifndef ASM_HEADER
#pragma once

info * init_info();

my_file * init_file(char * code_file);

functions * init_functions();

int run_func(functions * funcs, char * word, info * info, my_file * my_file);

int find_label(info * info, char  label);

int parse_command(functions * funcs, char * command, info * info, my_file * myFile, int isFromJmp);

void free_info(info * info);

void start_program(char * code_file);

#endif