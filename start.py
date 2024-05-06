import subprocess
import os

current_path = os.getcwd()

def run_separate_command(command):
  """Runs a command in a separate terminal window."""
  subprocess.Popen(command, shell=True)

commands = [
    "sbt run",
    f"cd {current_path}/src/main/python && start "" cmd /c \"start.bat\"",
    f"cd {current_path}/src/main/frontend/web && start "" cmd /c \"start.bat\""
]

# Run each command in a separate window
for command in commands:
  run_separate_command(command)

print("All commands initiated in separate windows.")