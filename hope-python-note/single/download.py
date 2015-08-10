import os
print(os.environ.get("M2_HOME"))
os.environ.update({"M2_HOME":"C:\\Program Files\\apache-maven-3.3.3"})
print(os.environ.get("M2_HOME"))