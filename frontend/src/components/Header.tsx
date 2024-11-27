import {Link, NavLink} from "react-router-dom";
import "./Header.css";
export default function Header() {
    return (
        <div>
            <nav className="navbar">
                <Link className="navbar-link" to={"/"}>Home</Link>
                <NavLink className="navbar-link" to={"/manage"}>Manage</NavLink>
                <NavLink className="navbar-link" to={"/storage"}>Storage</NavLink>
            </nav>
        </div>
    );
}