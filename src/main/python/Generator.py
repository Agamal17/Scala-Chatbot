from utils import *
from model import *

def generate(query: str):
    context = find_match(query)
    return conversation.invoke(input=f"Context:\n {context} \n\n {query}")['response']

if __name__ == "__main__":
    x = input("Enter Prompt: ")
    print(generate(x))