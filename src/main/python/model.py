from langchain.chains.conversation.base import ConversationChain
from langchain.chains.conversation.memory import ConversationBufferWindowMemory
from langchain_community.llms.huggingface_hub import HuggingFaceHub
from langchain.prompts import (
    SystemMessagePromptTemplate,
    HumanMessagePromptTemplate,
    ChatPromptTemplate,
    MessagesPlaceholder
)

import os
os.environ['HUGGINGFACEHUB_API_TOKEN'] = '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'


system_msg_template = SystemMessagePromptTemplate.from_template(template="""Answer the question as truthfully as possible using the provided context and user preferences, 
and if the answer is not contained within, say 'I don't have this information'""")


human_msg_template = HumanMessagePromptTemplate.from_template(template="{input}")

# prompt_template = ChatPromptTemplate.from_messages([system_msg_template, MessagesPlaceholder(variable_name="history"), human_msg_template])

llm = HuggingFaceHub(repo_id = "google/flan-t5-small", model_kwargs = {'max_length': 1024, 'temperature': 0})
# buffer_memory = ConversationBufferWindowMemory(k=3, return_message = True)
conversation = ConversationChain(llm=llm, verbose=True)
