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
            MinerFull #(entity_string to pass); try_transform_miner_full 
         Ore #(create_animation_action and schedule_animation to pass)
         OreBlob #
         Vein #(create_animation_action and schedule_animation to pass)
         Quake #(redefine get_rate as pass)
         
