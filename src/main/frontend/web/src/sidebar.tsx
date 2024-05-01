import React from 'react';

interface SidebarProps {
    // Add any props you want to pass to the sidebar, e.g., links, content
}

// @ts-ignore
const Sidebar: React.FC<SidebarProps> = ({ children }) => {
    return (
        <div className="sidebar">
            {children}
        </div>
    );
};

export default Sidebar;