<?php

    if (!isset($_SESSION)) { session_start(); }
    include('strings.php'); 
?>
<!-- main sidebar -->
    <aside id="sidebar_main">
        
        <div class="sidebar_main_header">
            <div class="sidebar_logo">
                <a href="info_main_app.php" class="sSidebar_hide"><img src="assets/img/<?php echo $Logo_img; ?>" alt=""  width="200"/></a>
                <a href="info_main_app.php" class="sSidebar_show"><img src="assets/img/logo_main_small.png" alt="" height="32" width="32"/></a>
            </div>
            
        </div>
        <div class="menu_section">
            <ul>
                <?php
                    $json_data = file_get_contents('partials/'.$_SESSION['CURRENT_SIDEBAR'].'.json');
                    $sPage =$_SESSION['CURRENT_DOC'];
                    foreach (json_decode($json_data, true) as $item) {
                ?>
                    <li<?php if($sPage == $item['url']) { echo ' class="current_section"'; }; ?> title="<?php echo $item['title']; ?>">
                    <?php if($item['rol'] == $_SESSION["Rol_Usuario"]){ ?>
                        <a href="<?php echo $item['url']; ?>">
                            <span class="menu_icon"><i class="material-icons"><?php echo $item['icon']; ?></i></span>
                            <span class="menu_title"><?php echo $item['title']; ?></span>
                        </a>
                        <?php }  ?>
                        <?php if(isset($item['submenu'])) { ?>
                            <ul>
                                <?php foreach ($item['submenu'] as $submenu_item) { ?>
                                    <?php if (isset($submenu_item['items'])) { ?>
                                        <li class="menu_subtitle"><?php echo $submenu_item['section_title']; ?></li>
                                        <?php foreach($submenu_item['items'] as $section_item) { ?>
                                            <li<?php if($sPage == $section_item['url']) { echo ' class="act_item"'; }; ?>><a href="<?php echo $section_item['url']; ?>.php"><?php echo $section_item['title']; ?></a></li>
                                        <?php }; ?>
                                    <?php } else { ?>
                                        <li<?php if($sPage == $submenu_item['url']) { echo ' class="act_item"'; }; ?>><a href="<?php echo $submenu_item['url']; ?>.php"><?php echo $submenu_item['title']; ?></a></li>
                                    <?php }; ?>
                                <?php }; ?>
                            </ul>
                        <?php }; ?>
                    </li>
                <?php }; ?>
                
            </ul>
        </div>
    </aside><!-- main sidebar end -->