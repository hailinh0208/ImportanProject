import { useState, useEffect } from 'react';

export interface Notice {
    id: number;
    noticeTitle: string;
    noticeContent: string;
    importantNoticeStart: string;
    importantNoticeEnd: string | null;
    displayBeforeOrAfterLogin: string;
}

interface UseImportantNoticeResult {
    notices: Notice[];
    loading: boolean;
    error: Error | null;
}

declare global {
    interface Window {
        Liferay?: {
            Util?: {
                fetch?: (url: string, options?: RequestInit) => Promise<Response>;
            };
        };
    }
}

export function useImportantNotice(pagecode: string, skip = false): UseImportantNoticeResult {
    const [notices, setNotices] = useState<Notice[]>([]);
    const [loading, setLoading] = useState(!skip);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        if (skip) return;

        const fetchFn =
            window.Liferay?.Util?.fetch?.bind(window.Liferay.Util) ??
            window.fetch.bind(window);

        fetchFn(`/o/headless-api/v1.0/cms-important-notification?pagecode=${pagecode}`)
            .then((res) => {
                if (!res.ok) throw new Error(`[ImportantNotice] API error: ${res.status}`);
                return res.json();
            })
            .then((data: { items?: Notice[] }) => setNotices(data.items ?? []))
            .catch((err: Error) => {
                console.error('[ImportantNotice] Fetch failed:', err);
                setError(err);
            })
            .finally(() => setLoading(false));
    }, [pagecode, skip]);

    return { notices, loading, error };
}
