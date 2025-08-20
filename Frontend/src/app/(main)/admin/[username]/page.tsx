export default async function AdminHome({ params }: { params: Promise<{ username: string }> }) {
	const { username } = await params;
	return (
		<div className="">
			<h1>Welcome {username}</h1>
			<p>ADMIN</p>
		</div>
	);
}
