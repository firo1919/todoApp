import { ImSpinner2 } from "react-icons/im";

function LoadingSpinner() {
	return (
		<div className="absolute bg-background top-0 w-full h-full opacity-50 flex items-center justify-center">
			<ImSpinner2 className="text-7xl animate-spin" />
		</div>
	);
}
export default LoadingSpinner;
