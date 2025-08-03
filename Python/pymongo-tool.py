from pymongo import MongoClient
import socket
from tkinter import *
from tkinter.ttk import *
import tkinter as tk
import ctypes
import os


class Application(tk.Frame,object):
    def __init__(self, master=None):
        super(Application,self).__init__(master)
        self.master.minsize(width=700, height=380)
        root.geometry('+%d+%d'%(100,30))
        self.clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client=self.dbConnect()
        self.create_widgets()
        
        #self.pack()

    def dbConnect(self):
        if os.path.exists("app.conf"):
            f=open("app.conf","r")
            CONNECTION_STRING=f.read()
        else:
            CONNECTION_STRING = "mongodb://192.168.1.199:27017"
        try:
            client = MongoClient(CONNECTION_STRING)
            return client
        except Exception as e:
            ctypes.windll.user32.MessageBoxW(0, str(e), "Data Base Error", 1)
        
    def date_click(self,event):
            self.Date.delete(0, 'end')
    def time_click(self,event):    
            self.Time.delete(0, 'end')
    def close_window(self):
        self.clientsocket.close()
        self.master.destroy()

    def Submit_apoint(self,event):
        if self.Name.get() and self.SurName.get() and self.Date.get() and  self.Time.get() :
            mydict = { "name": self.Name.get(), "surname": self.SurName.get(), "apoint":self.Date.get()+" "+self.Time.get()}
        else:
            ctypes.windll.user32.MessageBoxW(0, "Ολα τα πεδία είναι υποχρεωτικά", "Error", 1)
            return
        try:
             db=self.client.HealthNet.healt
             db.insert_one(mydict)
             ctypes.windll.user32.MessageBoxW(0, "Επιτυχής Καταχώριση", "", 1)
             self.Time.delete(0, 'end')
             self.Date.delete(0, 'end')
             self.SurName.delete(0, 'end')
             self.Name.delete(0, 'end')
             self.refresh_table()
        except Exception as e:
             ctypes.windll.user32.MessageBoxW(0, str(e), "Data Base Error", 1)

    def device_connection(self,event):
        db=self.client.HealthNet.healt
        item_details = db.find().sort("_id", -1)
        apointment=[]
        for i in item_details[:30]:
            d,t=str(i["apoint"]).split(" ")
            apointment.append((i["name"],i["surname"],d,t))
        self.clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.clientsocket.connect((self.manual_ip.get(), 8086))
        data=str(apointment).encode("utf-8")
        
        self.clientsocket.send(data)
        self.clientsocket.close()
        
    def next_patient(self,event):
        self.clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.clientsocket.connect((self.manual_ip.get(), 8086))
        self.clientsocket.sendall(b'next')
        self.clientsocket.close()

    def refresh_table(self):
        self.tree.delete(*self.tree.get_children())
        db=self.client.HealthNet.healt
        item_details = db.find().sort("_id", -1)
        for i in item_details:
           self.item = self.tree.insert("", "end", values=(i["name"],i["surname"],i["apoint"]))
    def create_widgets(self):
        self.label0 = Label(root, text="IP Συσκευής: ", font = "Calibri 14 bold", relief="flat")
        self.label0.grid(row=0,column=0,sticky=W)
        self.manual_ip = Entry(root)
        self.manual_ip.insert(END, "0.0.0.0")
        self.manual_ip.grid(row=0,column=0,sticky=W,padx=(100, 10))
        self.buttonScan=tk.Button(root,text="Σύνδεση",width=15, height=1,font="Calibri 12 bold",relief="raised")
        self.buttonScan.bind("<Button-1>", self.device_connection)
        self.buttonScan.grid(row=0,column=0,padx=(30, 10))
        lb_header=["Όνομα","Επώνυμο","Ημερομηνία","Ωρα"]
        self.tree = tk.ttk.Treeview(root,column=lb_header,show="headings", height=15)
        for i in range(0,3) :
            self.tree.column(lb_header[i], anchor=tk.N)

        self.tree.grid(row=2,column=0,pady=10)
        for col in lb_header:
            self.tree.heading(col, text=col.title())
        db=self.client.HealthNet.healt
        item_details = db.find().sort("_id", -1)
        for i in item_details:
           d,t=str(i["apoint"]).split(" ")
           self.item = self.tree.insert("", "end", values=(i["name"],i["surname"],d,t))
        vsb = tk.ttk.Scrollbar(root, orient="vertical", command=self.tree)
        vsb.grid(row=2,column=1,sticky=S+N)
        vsb.configure(command=self.tree.yview)
        self.tree.configure(yscrollcommand=vsb.set)
        
        self.label1= Label(root, text="Ονομα:", font = "Calibri 10 bold", relief="flat")
        self.label1.grid(row=3,column=0,sticky=W)
        self.Name= Entry(root)
        self.Name.grid(row=3,column=0,sticky=W,padx=(80, 10))
        self.label2= Label(root, text="Επώνυμο:", font = "Calibri 10 bold", relief="flat")
        self.label2.grid(row=4,column=0,sticky=W)
        self.SurName= Entry(root)
        self.SurName.grid(row=4,column=0,sticky=W,padx=(80, 10))
        self.label3= Label(root, text="Ημερομηνία:", font = "Calibri 10 bold", relief="flat")
        self.label3.grid(row=5,column=0,sticky=W)
        self.Date= Entry(root)
        self.Date.insert(END, "DD/MM/YYYY")
        self.Date.bind("<Button-1>", self.date_click)
        self.Date.grid(row=5,column=0,sticky=W,padx=(80, 10))
        self.label4= Label(root, text="Ωρα:", font = "Calibri 10 bold", relief="flat")
        self.label4.grid(row=6,column=0,sticky=W)
        self.Time= Entry(root)
        self.Time.insert(END, "00:00")
        self.Time.bind("<Button-1>", self.time_click)
        self.Time.grid(row=6,column=0,sticky=W,padx=(80, 10))
        self.buttonsubmit=tk.Button(root,text="Καταχώριση",width=15, height=1,font ="Calibri 10 bold",relief="raised")
        self.buttonsubmit.bind("<Button-1>", self.Submit_apoint)
        self.buttonsubmit.grid(row=7,column=0,sticky=W,padx=(80,10),pady=10)
        self.buttonsubmit=tk.Button(root,text="Επόμενος Ασθενής",width=15, height=5,font ="Calibri 12 bold",relief="raised")
        self.buttonsubmit.bind("<Button-1>", self.next_patient)
        self.buttonsubmit.grid(row=8,column=3,sticky=E,padx=(0,10),pady=10)
        

root = tk.Tk()
root.title('HealthNet App')
x = (root.winfo_screenwidth() - root.winfo_reqwidth()) / 2
y = (root.winfo_screenheight() - root.winfo_reqheight()) / 2
root.geometry("+%d+%d" % (x, y))
root.deiconify()
app = Application(master=root)
root.protocol("WM_DELETE_WINDOW", app.close_window) 
app.mainloop()

