#!/usr/bin/env bash

function la_complete_bootstrap
{
	COMPREPLY=()
	local cur="${COMP_WORDS[COMP_CWORD]}"
	local opts="help build Nightly Continuous Experimental"
	COMPREPLY=($(compgen -W "${opts}" -- ${cur}))
}

complete -F "la_complete_bootstrap" -o "nospace" filenames "bootstrap.sh"
