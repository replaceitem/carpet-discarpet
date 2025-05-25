"""
Copies all example scripts (starting with 'e_') from /runTest/world/scripts to
markdown codeblocks in /docs/include/generates/examples.
This is run by the mkdocs pre_build hook.
It replaces all environment usages in scripts to prompt the user to fill in their own IDs.
"""

import logging
import re
import shutil
from re import Match

import mkdocs.plugins
import os
from pathlib import Path

log = logging.getLogger('mkdocs')


example_script_pattern = re.compile(r'e_(.*)\.sc')

def on_pre_build(config: mkdocs.plugins.Config):
    include_dir = Path(config['plugins']['macros'].config['include_dir'])
    generated_dir = include_dir / 'generated'
    examples_dir = generated_dir / 'examples'
    shutil.rmtree(examples_dir, ignore_errors=True)
    os.makedirs(examples_dir, exist_ok=True)
    scripts_dir = Path().absolute().parent / 'runTest' / 'world' / 'scripts'
    for script in scripts_dir.glob(r'e_*.sc'):
        match = re.fullmatch(example_script_pattern, script.name)
        if match is None:
            continue
        script_name = match.group(1)
        script_content = script.read_text(encoding='UTF-8')
        cleaned_content = clean_script(script_content, script)
        example_file_name = script_name.replace('_', '-')
        example_path = examples_dir / f'{example_file_name}.md'
        example_path.write_text(f'```sc title="{script_name}.sc"\n{cleaned_content}\n```', encoding='UTF-8')


default_envs = {
    'channelId': "'put channel id here!'",
    'serverId': "'put server id here!'",
}
env_pattern = re.compile(r"env\('(\w+)'\)")

def clean_script(script: str, script_path: Path) -> str:
    script = script.replace("import('env','env');\n", '')
    
    def replace_env(match: Match[str]) -> str:
        env_name = match.group(1)
        replacement = default_envs.get(env_name)
        if not replacement:
            log.warning(f'Could not find replacement for env {env_name} in script {script_path}')
        return replacement or f"'{env_name}'"
    
    script = env_pattern.sub(replace_env, script)
    return script.strip()
    
    