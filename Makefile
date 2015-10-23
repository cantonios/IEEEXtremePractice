CPP_FILES := $(wildcard src/cpp/*.cpp)
BIN_FILES := $(addprefix bin/,$(notdir $(CPP_FILES:.cpp=)))
CPP_FLAGS := -g -O0

vars:
	@echo $(CPP_FILES)
	@echo $(BIN_FILES)

all: $(BIN_FILES)

bin/%: src/cpp/%.cpp
	g++ $(CPP_FLAGS) -o $@ $<