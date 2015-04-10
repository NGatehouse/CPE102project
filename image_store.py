import pygame

DEFAULT_IMAGE_NAME = 'background_default'
DEFAULT_IMAGE_COLOR = (128, 128, 128, 0)

def create_default_image(tile_width, tile_height): # uses data from the main.py file and uses the pygame function surface
   surf = pygame.Surface((tile_width, tile_height))
   surf.fill(DEFAULT_IMAGE_COLOR)
   return surf


def load_images(filename, tile_width, tile_height): # uses files and we don't have any classes that are made to deal with files
   images = {}
   with open(filename) as fstr:
      for line in fstr:
         process_image_line(images, line)

   if DEFAULT_IMAGE_NAME not in images:
      default_image = create_default_image(tile_width, tile_height)
      images[DEFAULT_IMAGE_NAME] = [default_image]

   return images


def process_image_line(images, line): # also deals with files and uses pygame methods
   attrs = line.split()
   if len(attrs) >= 2:
      key = attrs[0]
      img = pygame.image.load(attrs[1]).convert()      

      if img:
         imgs = get_images_internal(images, key)
         imgs.append(img)
         images[key] = imgs

         if len(attrs) == 6:
            r = int(attrs[2])
            g = int(attrs[3])
            b = int(attrs[4])
            a = int(attrs[5])
            img.set_colorkey(pygame.Color(r, g, b, a))


def get_images_internal(images, key): # data within this function doesn't match  any known class data.
   if key in images:
      return images[key]
   else:
      return []


def get_images(images, key): # getter that accesses data from the main.py file but no class data 
   if key in images:
      return images[key]
   else:
      return images[DEFAULT_IMAGE_NAME]
