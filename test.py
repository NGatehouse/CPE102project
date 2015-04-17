class Parent():
   def dosomething(x):
     x = x+1
     
     return x
class Child(Parent):
   def dosomething(x):
      x = x-1
      
      return x
      
   def doinsomething(func):
      self.dosomething(2)
