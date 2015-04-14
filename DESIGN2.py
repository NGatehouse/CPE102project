Entities.py
   
   Objects #get_images get_image next_image get_name set_position get_position
      Background #(redefine set_position and get_position to pass)
      Obstacle
      Time_management #scheduling class
      Actors #possibly next_possition and blob_next_possition from worldmodel.py
         #schedule_action get_rate get_pending_actions add_pending_actions remove_pending_actions clear_pending_actions entity_string create_animation_action create_entity_death_action schedule_animation 
         Blacksmith #(create_animation_action; schedule_animation; and create_entity_death_action to pass)
         Miner #create_miner_action try_transform_miner
            MinerNotFull #try_transform_miner_not_full miner_to_ore create_miner_not_full_action
            MinerFull #(entity_string to pass); try_transform_miner_full miner_to_smith
         Ore #(create_animation_action and schedule_animation to pass) create_ore_transform_action
         OreBlob #blob_to_vein create_ore_blob_action
         Vein #(create_animation_action and schedule_animation to pass) create_vein_action
         Quake #(redefine get_rate as pass)
         
#3 actors:  set_resource_count get_resource_count set_resource_limit
#4 actors (vein, ore, blacksmith don't have) get_animation_rate
#Should we have a create_action method in Actors
#do worldmodel.py and worldview.py; any others?

         
